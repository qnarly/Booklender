<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Информация о книге</title>
    <link rel="stylesheet" href="css/freemarker.css">
</head>
<body>
<div class="container">
    <#if book??>
        <#if book.imageUrl??>
            <img src="${book.imageUrl}" alt="${book.title}" style="max-width: 200px; float: left; margin-right: 20px;">
        </#if>

        <h1>${book.title}</h1>
        <h3>Автор: ${book.author}</h3>

        <p>${book.description!""}</p>

        <div style="clear: both;"></div>

        <p>
            Статус:
            <#if book.isTaken??>
                Книга на руках у сотрудника <b>${book.isTaken.name}</b>.
            <#else>
                <span style="color: green;">Книга свободна и находится в библиотеке.</span>
            </#if>
        </p>

        <#if user??>
            <div style="margin-top: 20px;">
                <#if book.isTaken??>
                    <#if book.isTaken.email == user.email>
                        <a href="/return?id=${book.id}&redirect=/book?id=${book.id}">Вернуть книгу</a>
                    </#if>
                <#else>
                    <a href="/checkout?id=${book.id}&redirect=/book?id=${book.id}">Взять книгу</a>
                </#if>
            </div>
        </#if>

    <#else>
        <h1>Книга не найдена</h1>
        <p>Книги с таким ID не существует в нашей базе.</p>
    </#if>
    <br>
    <a href="/books">Вернуться к списку книг</a>
</div>
</body>
</html>