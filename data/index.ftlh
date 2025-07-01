<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Главная страница</title>
    <link rel="stylesheet" href="css/forms.css">
    <style>
        .container {
            width: 800px;
            margin: 0 auto;
        }

        .nav-links {
            text-align: center;
            margin-top: 50px;
        }

        .nav-links a {
            display: inline-block;
            padding: 15px 25px;
            margin: 10px;
            font-size: 18px;
            color: white;
            background-color: #28a745;
            text-decoration: none;
            border-radius: 5px;
            transition: background-color 0.3s;
        }

        .nav-links a:hover {
            background-color: #218838;
        }
    </style>
</head>
<body>
<main>
    <div class="container">
        <#if user??>
            <p style="text-align: right;">Вы вошли как: <strong>${user.name}</strong></p>
        </#if>
        <div class="nav-links">
            <a href="/login">Войти</a>
            <a href="/register">Регистрация</a>
            <a href="/books">Список книг</a>
            <a href="/profile">Профиль</a>
        </div>
    </div>
</main>
</body>
</html>