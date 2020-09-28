package ua.aleks4ay.manufplan.domain.view;

import ua.aleks4ay.manufplan.domain.new_model.Description;
import ua.aleks4ay.manufplan.domain.new_model.Invoice;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.Set;

import static ua.aleks4ay.manufplan.domain.tools.DateConverter.dateToString;

public class ViewData {

    public void printHtml(List<Description> descriptions) {
        Writer writer = null;
        try {
            writer = new FileWriter(new File("print/file1.html"));
            writer.write("<!DOCTYPE html>" + "<html lang=\"en\">");
            writer.write("<head>" + "<meta charset=\"UTF-8\">");
            writer.write("<title>КИЙ-В</title>");
            writer.write("<link href=\"style1.css\" rel=\"stylesheet\" type=\"text/css\">");
            writer.write("</head>");
            writer.write("<body>");
            writer.write("<H1>Данные по Технологичке</H1>");
            writer.write("<div><table>");

            writer.write("<tr>");
            writer.write("<th style=\"width: 50px;\">IdTmc</th>");
            writer.write("<th style=\"width: 130px;\">Т М С</th>");
            writer.write("<th style=\"width: 130px;\">Клиент</th>");
            writer.write("<th style=\"width: 120px;\">Менеджер</th>");
            writer.write("<th style=\"width: 80px;\">Заказ (дата)</th>");
            writer.write("<th style=\"width: 70px;\">Дата (в произв.)</th>");
            writer.write("<th style=\"width: 30px;\">поз.</th>");
            writer.write("<th style=\"width: 250px;\">Описание</th>");
            writer.write("<th style=\"width: 50px;\">Длина</th>");
            writer.write("<th style=\"width: 50px;\">Ширина</th>");
            writer.write("<th style=\"width: 50px;\">Высота</th>");
            writer.write("<th style=\"width: 30px;\">Кол-во</th>");
            writer.write("<th style=\"width: 30px;\">Отгружено</th>");
            writer.write("<th style=\"width: 70px;\">Инвойс\n(дата)</th>");
            writer.write("<th style=\"width: 30px;\">На складе</th>");
            writer.write("</tr>");

            for (Description d : descriptions) {
                writer.write("<tr>");
                writer.write("<td>" + d.getTmc().getId() + "</td>");
                writer.write("<td>" + d.getTmc().getTmcDescription() + "</td>");
                writer.write("<td>" + d.getOrder().getClientName() + "</td>");
                writer.write("<td>" + d.getOrder().getManagerName() + "</td>");
                writer.write("<td>" + d.getOrder().getDocNumber() + "(" + dateToString(d.getOrder().getDateCreate().getTime()) +  ") </td>");
                writer.write("<td>" + dateToString(d.getOrder().getDateToFactory().getTime()) + "</td>");
                writer.write("<td>" + d.getPosition() + "</td>");
                writer.write("<td>" + d.getDescrSecond() + "</td>");
                writer.write("<td>" + d.getSizeA() + "</td>");
                writer.write("<td>" + d.getSizeB() + "</td>");
                writer.write("<td>" + d.getSizeC() + "</td>");
                writer.write("<td>" + d.getQuantity() + "</td>");
                writer.write("<td>" + d.getQuantityShipped() + "</td>");
                writer.write("<td>");
                Set<Invoice> invoices = d.getInvoices();
                for (Invoice invoice : invoices) {
                    writer.write(invoice.getDocNomberInvoice() + "(" + dateToString(invoice.getTimeInvoice().getTime()) + ")");
                }
                writer.write("</td>");
                writer.write("<td>" + d.getTmc().getBalance() + "</td>");
                writer.write("</tr>");


            }

            writer.write("</table></div>");
            writer.write("");
            writer.write("");
            writer.write("</body>");
            writer.write("</html>");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
