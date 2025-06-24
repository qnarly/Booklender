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
        <h1>${book.title}</h1>
        <h3>Автор: ${book.author}</h3>
        <p>
            Статус:
            <#if book.isTaken??>
                Книга на руках у сотрудника <b>${book.isTaken.name}</b>.
            <#else>
                <span style="color: green;">Книга свободна и находится в библиотеке.</span>
            </#if>
        </p>
    <#else>
        <h1>Книга не найдена</h1>
        <p>Книги с таким ID не существует в нашей базе.</p>
    </#if>
    <br>
    <a href="/books">Вернуться к списку книг</a>
</div>
</body>
</html>