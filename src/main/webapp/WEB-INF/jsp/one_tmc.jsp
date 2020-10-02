<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page language="java" pageEncoding="UTF-8"%>

<html>
<head>
    <title>КИЙ-В</title>
    <style>
        TABLE {
            border-collapse: collapse; /* Убираем двойные линии между ячейками */
            table-layout: fixed; /*запрет растягивать колонки*/
            /*width: 1500px; !* Ширина таблицы *!*/
            width: 49%
        }
        TH {
            border: 1px solid #1a851e; /* Параметры рамки */
            text-align: center; /* Выравнивание по центру */
            padding: 4px; /* Поля вокруг текста */
            background: rgba(255, 209, 0, 0.56); /* Цвет фона ячейки */
            height: 40px; /* Высота ячеек */
            vertical-align: middle; /* Выравнивание по нижнему краю */
            padding: 0; /* Убираем поля вокруг текста */
        }
        TD {
            border: 1px solid #1a851e; /* Параметры рамки */
            text-align: center; /* Выравнивание по центру */
            vertical-align: middle; /* Выравнивание по нижнему краю */
            padding: 2px; /* Поля вокруг текста */
            overflow-x: hidden;
            overflow-y: visible;
            word-wrap:break-word; /*Разбиение длинных слов*/
        }
        .date {
            width: 150px;
            font-size: 18px;
            padding: 6px 0 4px 10px;
            border: 1px solid #cecece;
            background: #F6F6f6;
            border-radius: 8px;
        }
    </style>
</head>
<body>

    <H1>Данные по Технологичке, записей ${numberOfReports} <i>(${beginStringDay} - ${endStringDay}) </i> </H1>

    <h3><a href="/tmc">Общая таблица технологической продукции</a> </h3>
    <div>
        <table style="font-size: 14px; text-align: center;">
            <tr class="red" style="height: 1.8em">
                <th width="39.64%">Т М С</th>
                <th width="8.24%">Длина</th>
                <th width="8.24%">Ширина</th>
                <th width="8.24%">Высота</th>
                <th width="8.88%">Нужно всего</th>
                <th width="8.88%">На складе</th>
                <th width="9%">Заброни- ровано</th>
                <th width="8.88%">Не хватает</th>
            </tr>
            <tr>
                <c:set var="rowSpan1" value="${2}" />
                <c:if test="${empty oneTmc.descrSecond}" >
                    <c:set var="rowSpan1" value="${1}" />
                </c:if>
                <td style="text-align: left;"> ${oneTmc.tmcDescription} </td>
                <td rowspan="${rowSpan1}"> ${oneTmc.sizeA} </td>
                <td rowspan="${rowSpan1}"> ${oneTmc.sizeB} </td>
                <td rowspan="${rowSpan1}"> ${oneTmc.sizeC} </td>
                <td rowspan="${rowSpan1}" style="font-size: 26px"> ${oneTmc.demand} </td> <%--нужно всего--%>
                <td rowspan="${rowSpan1}" style="font-size: 26px"> ${oneTmc.balance} </td>
                <td rowspan="${rowSpan1}" style="font-size: 26px"> (***) </td>
                <td rowspan="${rowSpan1}" style="font-size: 26px">
                    <c:set var="quant" value="${oneTmc.demand - oneTmc.balance}" />
                    <c:if test="${quant > 0}" > ${quant} </c:if>
                </td> <%--не хватает--%>
            </tr>
            <c:if test="${! empty oneTmc.descrSecond}" >
                <tr>
                    <td style="text-align: left;">
                        ${oneTmc.descrSecond}
                    </td>
                </tr>
            </c:if>
        </table>
    </div>
    <br/>
    <div>
        <table style="font-size: 14px; text-align: center">
            <tr class="red" style="height: 1.8em">
                <th width="21.39%">Заказ</th>
                <th width="9.63%">Дата (в&nbsp;произв.)</th>
                <th width="17.39%">Клиент</th>
                <th width="12.44%">Менеджер</th>
                <th width="3.74%">Кол-во</th>
                <th width="5.88%">Отгру- жено</th>
                <th width="17.65%">Инвойс (дата)</th>
                <th width="11.88%" colspan="3">Бронь</th>
            </tr>

            <c:forEach var="descr" items="${oneTmc.descriptions}" varStatus="pos">
            <tr>
                <td style="text-align: left;">
                    ${descr.docNumber}, поз.${descr.position} <i>(${descr.dateCreate})</i>
                </td>
                <td> ${descr.dateToFactory} </td>
                <td style="text-align: left;"> ${descr.client} </td>
                <td style="text-align: left;"> ${descr.manager} </td>
                <td> ${descr.quantity} </td>
                <td> ${descr.quantityShipped} </td>
                <td>
                    <c:if test="${!empty descr.invoices}">
                        <c:forEach var="invoice" items="${descr.invoices}" varStatus="i">
                            ${invoice.docNomberInvoice} (${invoice.timeInvoiceString})<br/>
                        </c:forEach>
                    </c:if>
                </td>
                <td>
                    0
                </td>
                <td colspan="2">
                    <a href="/tmc/one/change/id">Изменить </a>
                </td>
            </tr>
            </c:forEach>
        </table>
    </div>
</body>
</html>
