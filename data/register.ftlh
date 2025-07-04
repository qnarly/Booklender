<#import "layout.ftlh" as layout>

<@layout.page title="Регистрация">
    <div class="row justify-content-center mt-5">
        <div class="col-md-6 col-lg-5">
            <div class="card shadow-sm">
                <div class="card-body p-4">
                    <h3 class="card-title text-center mb-4">Создание аккаунта</h3>
                    <form action="/register" method="post">
                        <fieldset>
                            <div class="form-floating mb-3">
                                <input type="text" name="nickname" id="user-nickname" class="form-control"
                                       placeholder="Username" required>
                                <label for="user-nickname">Ваше имя</label>
                            </div>
                            <div class="form-floating mb-3">
                                <input type="email" name="email" id="user-email" class="form-control"
                                       placeholder="E-mail"
                                       required>
                                <label for="user-email">Email</label>
                            </div>
                            <div class="form-floating mb-3">
                                <input type="password" name="user-password" id="user-password" class="form-control"
                                       placeholder="Password" required>
                                <label for="user-password">Пароль</label>
                            </div>
                            <div class="d-grid">
                                <button class="btn btn-primary btn-lg" type="submit">Зарегистрироваться</button>
                            </div>
                        </fieldset>
                    </form>
                    <#if message??>
                        <div class="alert alert-danger text-center mt-3 mb-0">
                            ${message}
                        </div>
                    </#if>
                </div>
                <div class="card-footer text-center py-3">
                    <div class="small">Уже есть аккаунт? <a href="/login">Войти</a></div>
                </div>
            </div>
        </div>
    </div>
</@layout.page>
