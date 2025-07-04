<#import "layout.ftlh" as layout>

<@layout.page title="Профиль">
<#if user??>
    <div class="row">
        <div class="col-md-4">
            <div class="card">
                <div class="card-header">
                    <h4>Профиль</h4>
                </div>
                <div class="card-body">
                    <p><strong>Имя:</strong> ${user.name}</p>
                    <p><strong>Email:</strong> ${user.email}</p>
                    <a href="/logout" class="btn btn-outline-danger mt-2">Выйти из аккаунта</a>
                </div>
            </div>
        </div>
        <div class="col-md-8">
            <h4>Книги на руках</h4>
            <#if books?? && books?has_content>
                <ul class="list-group">
                    <#list books as book>
                        <li class="list-group-item d-flex justify-content-between align-items-center">
                            <span>${book.title} (${book.author})</span>
                            <a href="/return?id=${book.id}&redirect=/profile" class="btn btn-danger btn-sm">Вернуть</a>
                        </li>
                    </#list>
                </ul>
            <#else>
                <p class="text-muted">У вас нет книг на руках.</p>
            </#if>

            <h4 class="mt-4">Прочитано в этой сессии</h4>
            <#if exBooks?? && exBooks?has_content>
                <ul class="list-group">
                    <#list exBooks as book>
                        <li class="list-group-item">
                            ${book.title} (${book.author})
                        </li>
                    </#list>
                </ul>
            <#else>
                <p class="text-muted">Вы еще не возвращали книг в этой сессии.</p>
            </#if>
        </div>
    </div>
<#else>
    <div class="alert alert-danger">Не удалось загрузить информацию о профиле.</div>
</#if>
</@layout.page>
