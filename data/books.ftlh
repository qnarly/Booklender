<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Список книг</title>
    <link rel="stylesheet" href="css/freemarker.css">
</head>
<body>
<div class="container">
    <h1>Наша библиотека</h1>
    <div class="table flex flex-col">
        <div class="header flex">
            <div class="column">ID</div>
            <div class="column">Название</div>
            <div class="column">Автор</div>
            <div class="column">Статус</div>
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