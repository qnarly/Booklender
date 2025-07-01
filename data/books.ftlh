<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Список книг</title>
    <link rel="stylesheet" href="css/freemarker.css">
</head>
<body>
<div class="container">
    <#if user??>
        <p style="text-align: right;">Вы вошли как: <strong>${user.name}</strong></p>
    </#if>
    <h1>Наша библиотека</h1>
    <div class="table flex flex-col">
        <div class="header flex">
            <div class="column">ID</div>
            <div class="column">Название</div>
            <div class="column">Автор</div>
            <div class="column">Статус</div>
            <#if user??>
                <div class="column">Действие</div>
            </#if>
        </div>
        <#list books as book>
            <div class="row ${book?item_parity} flex">
                <div class="column">${book.id}</div>
                <div class="column">
                    <a href="/book?id=${book.id}">${book.title}</a>
                </div>
                <div class="column">${book.author}</div>
                <div class="column">
                    <#if book.isTaken??>
                        Выдана: ${book.isTaken.name}
                    <#else>
                        <span style="color: green;">В наличии</span>
                    </#if>
                </div>
                <#if user??>
                    <div class="column">
                        <#if book.isTaken??>
                            <#if book.isTaken.email == user.email>
                                <a href="/return?id=${book.id}">Вернуть</a>
                            </#if>
                        <#else>
                            <a style="text-decoration: none" href="/checkout?id=${book.id}">Арендовать</a>
                        </#if>
                    </div>
                </#if>
            </div>
        </#list>
    </div>
    <br>
    <div style="text-align: center;">
        <a href="/profile">Мой профиль</a> |
        <a href="/">На главную</a>
    </div>
</div>
</body>
</html>