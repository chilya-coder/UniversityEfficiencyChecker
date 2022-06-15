package com.chimyrys.universityefficiencychecker.services;

import com.chimyrys.universityefficiencychecker.db.ContractRepository;
import com.chimyrys.universityefficiencychecker.db.RequirementTypeRepository;
import com.chimyrys.universityefficiencychecker.db.ScienceWorkRepository;
import com.chimyrys.universityefficiencychecker.db.UserRepository;
import com.chimyrys.universityefficiencychecker.model.*;
import com.chimyrys.universityefficiencychecker.model.condition.Condition;
import com.chimyrys.universityefficiencychecker.services.api.GenerateWordDocumentService;
import com.chimyrys.universityefficiencychecker.services.api.UserService;
import com.chimyrys.universityefficiencychecker.utils.ConditionUtils;
import com.chimyrys.universityefficiencychecker.utils.DateUtils;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.usermodel.*;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class GenerateWordDocumentServiceImpl implements GenerateWordDocumentService {
    private final UserService userService;
    private final ScienceWorkRepository scienceWorkRepository;
    private final UserRepository userRepository;
    private final RequirementTypeRepository requirementTypeRepository;
    private final ContractRepository contractRepository;

    public GenerateWordDocumentServiceImpl(UserService userService, ScienceWorkRepository scienceWorkRepository, UserRepository userRepository, RequirementTypeRepository requirementTypeRepository, ContractRepository contractRepository) {
        this.userService = userService;
        this.scienceWorkRepository = scienceWorkRepository;
        this.userRepository = userRepository;
        this.requirementTypeRepository = requirementTypeRepository;
        this.contractRepository = contractRepository;
    }

    @Override
    public byte[] generateReport(int yearSince, int yearTo) {
        User user = userService.getCurrentUser();
        DateFormat publicationDateFormat = new SimpleDateFormat("dd.MM.yyyy");
        XWPFDocument doc = null;
        try {
            doc = new XWPFDocument(new FileInputStream("src/main/resources/templates/report_template.docx"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        List<Date> dates = DateUtils.getDateRangeForYear(yearSince, yearTo);
        try {
            for (XWPFParagraph paragraph : doc.getParagraphs()) {
                for (XWPFRun run : paragraph.getRuns()) {
                    String text = run.getText(0);
                    text = text.replace("fullName", user.getFullName());
                    text = text.replace("yearSince", String.valueOf(yearSince));
                    text = text.replace("yearTo", String.valueOf(yearTo));
                    run.setText(text, 0);
                }
            }
            List<ScienceWork> scienceWorkList =
                    scienceWorkRepository
                            .findAllByDateOfPublicationBetweenAndUsersEqualsOrderByDateOfPublicationAsc(dates.get(0), dates.get(1), user);
            XWPFTable table = doc.createTable(scienceWorkList.size() + 1, 6);

            table.getRow(0).getCell(0).setText("№ з/п");
            table.getRow(0).getCell(1).setText("Назва");
            table.getRow(0).getCell(2).setText("Характер роботи");
            table.getRow(0).getCell(3).setText("Вхідні дані");
            table.getRow(0).getCell(4).setText("Обсяг (стор.)");
            table.getRow(0).getCell(5).setText("Співавтори");
            for (int i = 0; i < scienceWorkList.size(); i++) {
                ScienceWork scienceWork = scienceWorkList.get(i);
                XWPFTableRow row = table.getRow(i + 1);
                row.getCell(0).setText(String.valueOf(i + 1));
                row.getCell(1).setText(scienceWork.getName() + " (" + scienceWork.getTypeOfWork().getType() + ")");
                row.getCell(2).setText(scienceWork.getCharOfWork().getCharacteristic());
                row.getCell(3).setText(scienceWork.getOutputData()
                        + "\n" + "Дата публікації: " + publicationDateFormat.format(scienceWork.getDateOfPublication()));
                row.getCell(4).setText(String.valueOf(scienceWork.getSize()));
                row.getCell(5).setText(scienceWork.getCoAuthorsNames(user));
            }
            doc.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
                //doc.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return out.toByteArray();
    }

    @Override
    public byte[] generateActivityReport() {
        XSSFWorkbook workbook = null;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            workbook = new XSSFWorkbook(new FileInputStream("src/main/resources/templates/activityTemplate.xlsx"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            XSSFSheet sheet = workbook.getSheetAt(0);
            List<User> users = userRepository.findAll();
            for (int i = 0; i < users.size(); i++) {
                User user = users.get(i);
                if (user.getScienceWorks().size() == 0) {
                    continue;
                }
                List<Specialty> specialties = user.getScienceWorks()
                        .stream()
                        .map(ScienceWork::getSpecialty)
                        .distinct()
                        .collect(Collectors.toList());
                XSSFRow row = sheet.getRow(5 + i);
                row.getCell(0).setCellValue(i);
                row.getCell(1).setCellValue(user.getDepartment().getName());
                row.getCell(2).setCellValue(user.getFullName());
                row.getCell(3).setCellValue(user.getDegree().getName() + ", " + user.getScientificTitle().getName());
                row.getCell(4).setCellValue(specialties.stream()
                        .map(specialty -> specialty.getCode() + " " + specialty.getName() + " (" + specialty.getSpecialtyAlias() + ")")
                        .collect(Collectors.joining(", ")));
                if (specialties.size() == 1) {
                    row.getCell(5).setCellValue(getAmountOfRequirementTypeForUser(user, specialties.get(0)));
                    List<RequirementType> requirementTypeList = getCompletedRequirementTypeForUser(user, specialties.get(0));
                    row.getCell(6).setCellValue(requirementTypeList
                            .stream()
                            .map(r -> String.valueOf(r.getReqNumber()))
                            .collect(Collectors.joining(", ")));
                } else {
                    row.getCell(5).setCellValue(specialties
                            .stream()
                            .filter(specialty -> getAmountOfRequirementTypeForUser(user, specialty) != 0)
                            .map(specialty -> specialty.getSpecialtyAlias() + " - " + getAmountOfRequirementTypeForUser(user, specialty))
                            .collect(Collectors.joining(", ")));
                    row.getCell(6).setCellValue(specialties
                            .stream()
                            .filter(specialty -> getAmountOfRequirementTypeForUser(user, specialty) != 0)
                            .map(specialty -> specialty.getSpecialtyAlias() + " - " + getCompletedRequirementTypeForUser(user, specialty)
                                    .stream()
                                    .map(r -> String.valueOf(r.getReqNumber())).collect(Collectors.joining(", ")))
                            .collect(Collectors.joining(", ")));
                }
            }
            workbook.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return out.toByteArray();
    }

    @Override
    public byte[] generateInfoReport() {
        User user = userService.getCurrentUser();
        List<ScienceWork> scienceWorks = user.getScienceWorks();
        DateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        Contract contract = contractRepository.findFirstByUserOrderByDateStartDesc(user).get();
        String startLastContract = format.format(contract.getDateStart());
        String endLastContract = format.format(contract.getDateEnd());
        Date date5yearsAgo = new Date();
        date5yearsAgo.setYear(date5yearsAgo.getYear() - 5);
        XWPFDocument doc = null;
        try {
            doc = new XWPFDocument(new FileInputStream("src/main/resources/templates/info_report.docx"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            for (XWPFParagraph paragraph : doc.getParagraphs()) {
                for (XWPFRun run : paragraph.getRuns()) {
                    String text = run.getText(0);
                    if (text == null) {
                        continue;
                    }
                    text = text.replace("name", user.getFullName());
                    text = text.replace("stitle", user.getScientificTitle().getName());
                    text = text.replace("degree", user.getDegree().getName());
                    text = text.replace("department", user.getDepartment().getName());
                    text = text.replace("year", String.valueOf(getDiffYears(contract.getDateStart(), new Date())));
                    text = text.replace("start", startLastContract);
                    text = text.replace("end", endLastContract);
                    run.setText(text, 0);
                }
            }
            XWPFTable firstTable = doc.getTableArray(0);
            XWPFTable secondTable = doc.getTableArray(2);
            List<RequirementType> requirementTypes = requirementTypeRepository.findAll();
            for (int i = 0; i < requirementTypes.size(); i++) {
                XWPFTableRow firstTableRow;
                XWPFTableRow secondTableRow;
                if (i == 0) {
                    firstTableRow = firstTable.getRow(i + 4);
                    secondTableRow = secondTable.getRow(i + 1);
                } else {
                    firstTableRow = firstTable.createRow();
                    firstTableRow.addNewTableCell();
                    secondTableRow = secondTable.createRow();
                }
                RequirementType requirementType = requirementTypes.get(i);
                firstTableRow.getCell(0).setText("2." + (i + 1));

                firstTableRow.getCell(1).setText(requirementType.getName());
                if (requirementType.getCondition() == null || requirementType.getCondition().isEmpty()) {
                    continue;
                }
                List<ScienceWork> match5year = getScienceWorkMatchReqTypeBetweenDate(scienceWorks, requirementType, date5yearsAgo, new Date());
                List<ScienceWork> matchContract = getScienceWorkMatchReqTypeBetweenDate(scienceWorks, requirementType, contract.getDateStart(), contract.getDateEnd());
                firstTableRow.getCell(3).setText(String.valueOf(match5year.size()));
                firstTableRow.getCell(4).setText(String.valueOf(matchContract.size()));
                //match5year.addAll(matchContract);
                if (!match5year.isEmpty()) {
                    secondTableRow.getCell(0).setText("2." + (i + 1));
                    secondTableRow.getCell(1).setText("Позиції " + match5year.stream().map(x -> String.valueOf(x.getScienceWorkId())).collect(Collectors.joining(", ")) + " списку публікацій, який додається до інформаційної довідки.");
                }
            }
            doc.write(out);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return out.toByteArray();
    }

    private int getAmountOfRequirementTypeForUser(User user, Specialty specialty) {
        int amount = 0;
        List<ScienceWork> scienceWorks = user.getScienceWorks()
                .stream()
                .filter(work -> work.getSpecialty() == specialty)
                .filter(work -> {
                    Date date5yearsAgo = new Date();
                    date5yearsAgo.setYear(date5yearsAgo.getYear() - 5);
                    return work.getDateOfPublication().compareTo(date5yearsAgo) >= 0;
                })
                .collect(Collectors.toList());
        List<RequirementType> requirementTypes = requirementTypeRepository.findAll();
        for (RequirementType requirementType : requirementTypes) {
            if (requirementType.getCondition() == null || requirementType.getCondition().isEmpty()) {
                continue;
            }
            Condition condition = ConditionUtils.getConditionFromString(requirementType.getCondition());
            for (ScienceWork scienceWork : scienceWorks) {
                if (condition.isValid(scienceWork)) {
                    amount++;
                    break;
                }
            }
        }
        return amount;
    }

    private List<RequirementType> getCompletedRequirementTypeForUser(User user, Specialty specialty) {
        List<RequirementType> completedRequirementTypes = new ArrayList<>();
        List<ScienceWork> scienceWorks = user.getScienceWorks()
                .stream()
                .filter(work -> work.getSpecialty() == specialty)
                .filter(work -> {
                    Date date5yearsAgo = new Date();
                    date5yearsAgo.setYear(date5yearsAgo.getYear() - 5);
                    return work.getDateOfPublication().compareTo(date5yearsAgo) >= 0;
                })
                .collect(Collectors.toList());
        List<RequirementType> requirementTypes = requirementTypeRepository.findAll();
        for (RequirementType requirementType : requirementTypes) {
            if (requirementType.getCondition() == null || requirementType.getCondition().isEmpty()) {
                continue;
            }
            Condition condition = ConditionUtils.getConditionFromString(requirementType.getCondition());
            for (ScienceWork scienceWork : scienceWorks) {
                if (condition.isValid(scienceWork)) {
                    completedRequirementTypes.add(requirementType);
                    break;
                }
            }
        }
        completedRequirementTypes = completedRequirementTypes.stream().distinct().collect(Collectors.toList());
        completedRequirementTypes.sort(Comparator.comparingInt(RequirementType::getReqTypeId));
        return completedRequirementTypes;
    }

    public static int getDiffYears(Date first, Date last) {
        Calendar a = getCalendar(first);
        Calendar b = getCalendar(last);
        int diff = b.get(Calendar.YEAR) - a.get(Calendar.YEAR);
        if (a.get(Calendar.MONTH) > b.get(Calendar.MONTH) ||
                (a.get(Calendar.MONTH) == b.get(Calendar.MONTH) && a.get(Calendar.DATE) > b.get(Calendar.DATE))) {
            diff--;
        }
        return diff;
    }

    public static Calendar getCalendar(Date date) {
        Calendar cal = Calendar.getInstance(Locale.US);
        cal.setTime(date);
        return cal;
    }

    public List<ScienceWork> getScienceWorkMatchReqTypeBetweenDate(List<ScienceWork> scienceWorks, RequirementType requirementType, Date start, Date end) {
        List<ScienceWork> match = new ArrayList<>();
        scienceWorks = scienceWorks
                .stream()
                .filter(x -> x.getDateOfPublication().after(start))
                .filter(x -> x.getDateOfPublication().before(end))
                .collect(Collectors.toList());
        Condition condition = ConditionUtils.getConditionFromString(requirementType.getCondition());
        for (ScienceWork scienceWork : scienceWorks) {
            if (condition.isValid(scienceWork)) {
                match.add(scienceWork);
            }
        }
        return match;
    }
}
