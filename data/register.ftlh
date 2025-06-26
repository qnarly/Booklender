<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <title>Login form</title>
    <link rel="stylesheet" href="css/forms.css">
</head>

<body>
<main>
    <form action="/login" method="post">
        <fieldset>
            <div class="legend">
                <p>Welcome!</p>
                <img src="/images/1.jpg" alt="coins" style="display:block; width: 200px; height: 200px;">
            </div>
            <div class="form-element">
                <label for="user-email">E-mail</label>
                <input type="email" name="email" id="user-email" placeholder="E-mail" required autofocus>
            </div>
            <div class="form-element">
                <label for="user-nickname">Username</label>
                <input type="text" name="nickname" id="user-nickname" placeholder="Username" required autofocus>
            </div>
            <div class="form-element">
                <label for="user-password">Password</label>
                <input type="password" name="user-password" id="user-password" placeholder="Password" required>
            </div>
            <div class="hr-line">
                <span class="details">one more step to go</span>
            </div>
            <div class="form-element">
                <button class="register-button" type="submit">Register!</button>
            </div>
        </fieldset>
    </form>
</main>
</body>

</html>