<#import "layout.ftlh" as layout>

<@layout.page title="Каталог книг">
<div class="d-flex justify-content-between align-items-center mb-3">
    <h1>Каталог книг</h1>
</div>

<div class="table-responsive">
    <table class="table table-striped table-hover align-middle">
        <thead class="table-dark">
        <tr>
            <th scope="col">#</th>
            <th scope="col">Название</th>
            <th scope="col">Автор</th>
            <th scope="col">Статус</th>
            <#if user??>
                <th scope="col">Действие</th></#if>
        </tr>
        </thead>
        <tbody>
        <#list books as book>
            <tr>
                <th scope="row">${book.id}</th>
                <td><a href="/book?id=${book.id}" class="text-decoration-none">${book.title}</a></td>
                <td>${book.author}</td>
                <td>
                    <#if book.isTaken??>
                        <span class="badge bg-secondary">Выдана: ${book.isTaken.name}</span>
                    <#else>
                        <span class="badge bg-success">В наличии</span>
                    </#if>
                </td>
                <#if user??>
                    <td>
                        <#if book.isTaken??>
                            <#if book.isTaken.email == user.email>
                                <a href="/return?id=${book.id}" class="btn btn-outline-danger btn-sm">Вернуть</a>
                            </#if>
                        <#else>
                            <a href="/checkout?id=${book.id}" class="btn btn-outline-primary btn-sm">Взять книгу</a>
                        </#if>
                    </td>
                </#if>
            </tr>
        </#list>
        </tbody>
    </table>
</div>
</@layout.page>
