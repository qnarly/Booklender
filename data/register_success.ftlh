<#import "layout.ftlh" as layout>

<@layout.page title="Регистрация успешна">

    <div class="alert alert-success mt-5 p-4 text-center shadow-sm">
        <h1 class="alert-heading">Отлично!</h1>
        <p class="lead">Ваш аккаунт был успешно создан.</p>
        <hr>
        <p class="mb-0">Теперь вы можете войти в систему, используя ваш email и пароль.</p>
        <div class="mt-4">
            <a class="btn btn-primary btn-lg" href="/login" role="button">Перейти на страницу входа</a>
        </div>
    </div>

</@layout.page>