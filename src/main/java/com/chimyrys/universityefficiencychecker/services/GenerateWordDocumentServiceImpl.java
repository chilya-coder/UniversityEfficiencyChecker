package com.chimyrys.universityefficiencychecker.services;

import com.chimyrys.universityefficiencychecker.db.ScienceWorkRepository;
import com.chimyrys.universityefficiencychecker.model.ScienceWork;
import com.chimyrys.universityefficiencychecker.model.User;
import com.chimyrys.universityefficiencychecker.services.api.GenerateWordDocumentService;
import com.chimyrys.universityefficiencychecker.services.api.UserService;
import com.chimyrys.universityefficiencychecker.utils.DateUtils;
import org.apache.poi.xwpf.usermodel.*;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class GenerateWordDocumentServiceImpl implements GenerateWordDocumentService {
    private final UserService userService;
    private final ScienceWorkRepository scienceWorkRepository;

    public GenerateWordDocumentServiceImpl(UserService userService, ScienceWorkRepository scienceWorkRepository) {
        this.userService = userService;
        this.scienceWorkRepository = scienceWorkRepository;
    }

    @Override
    public byte[] createTableWord(int yearSince, int yearTo) {
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
                    run.setText(text,0);
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
            for(int i = 0; i < scienceWorkList.size(); i++) {
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


}
