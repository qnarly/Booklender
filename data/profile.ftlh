<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Профиль пользователя</title>
    <link rel="stylesheet" href="css/freemarker.css">
    <style>
        .profile-card {
            background-color: #f9f9f9;
            border: 1px solid #ddd;
            border-radius: 8px;
            padding: 24px;
            max-width: 500px;
            margin: 40px auto;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        }

        .profile-card h1 {
            color: #333;
            border-bottom: 2px solid #eee;
            padding-bottom: 10px;
            margin-top: 0;
        }

        .profile-info p {
            font-size: 18px;
            line-height: 1.6;
        }

        .profile-info strong {
            color: #555;
            min-width: 120px;
            display: inline-block;
        }
    </style>
</head>
<body>
<div class="container">

    <#if user??>
        <div class="profile-card">
            <h1>Профиль пользователя</h1>
            <div class="profile-info">
                <p><strong>Имя:</strong> ${user.name}</p>
                <p><strong>Email:</strong> ${user.email}</p>
            </div>
        </div>
    <#else>
        <h1>Пользователь не найден</h1>
        <p>Не удалось загрузить информацию о профиле.</p>
    </#if>

    <br>
    <div style="text-align: center;">
        <a href="/books">Перейти к списку книг</a> |
        <a href="/">На главную</a>
    </div>
</div>
</body>
</html>