<#import "layout.ftlh" as layout>

<@layout.page title="Booklender">

<div class="p-5 mb-4 bg-light rounded-3">
    <div class="container-fluid py-5">
        <h1 class="display-5 fw-bold">Добро пожаловать в нашу библиотеку!</h1>
        <p class="col-md-8 fs-4">
            Здесь вы можете просматривать наш каталог книг, брать их почитать и отслеживать свою историю чтения.
        </p>
        <a class="btn btn-primary btn-lg" href="/books" role="button">Перейти к списку книг »</a>
    </div>
</div>

<div class="row">
    <#if user??>
        <div class="col-md-6">
            <div class="card">
                <div class="card-body">
                    <h5 class="card-title">Ваш профиль</h5>
                    <p class="card-text">Просматривайте книги, которые сейчас у вас на руках, и историю ваших
                        прочтений.</p>
                    <a href="/profile" class="btn btn-secondary">Перейти в профиль</a>
                </div>
            </div>
        </div>
        <div class="col-md-6">
            <div class="card">
                <div class="card-body">
                    <h5 class="card-title">Выход</h5>
                    <p class="card-text">Завершить текущую сессию и выйти из аккаунта.</p>
                    <a href="/logout" class="btn btn-secondary">Выйти</a>
                </div>
            </div>
        </div>
    <#else>
        <div class="col-md-6">
            <div class="card">
                <div class="card-body">
                    <h5 class="card-title">Вход в аккаунт</h5>
                    <p class="card-text">Уже есть аккаунт? Войдите, чтобы получить доступ к вашему профилю и книгам.</p>
                    <a href="/login" class="btn btn-secondary">Войти</a>
                </div>
            </div>
        </div>
        <div class="col-md-6">
            <div class="card">
                <div class="card-body">
                    <h5 class="card-title">Регистрация</h5>
                    <p class="card-text">Нет аккаунта? Присоединяйтесь к нам, чтобы получить полный доступ к
                        возможностям библиотеки.</p>
                    <a href="/register" class="btn btn-secondary">Зарегистрироваться</a>
                </div>
            </div>
        </div>
    </#if>
</div>
</@layout.page>