<#import "layout.ftlh" as layout>

<@layout.page title="Книги">
<#if book??>
    <div class="row">
        <div class="col-md-4">
            <#if book.imageUrl??>
                <img src="${book.imageUrl}" alt="${book.title}" class="img-fluid rounded shadow-sm">
            <#else>
                <img src="https://via.placeholder.com/400x600.png?text=No+Image" alt="No image"
                     class="img-fluid rounded shadow-sm">
            </#if>
        </div>
        <div class="col-md-8">
            <h1 class="mb-0">${book.title}</h1>
            <h3 class="text-muted">${book.author}</h3>
            <hr>
            <p class="lead">${book.description!""}</p>
            <hr>

            <h4>Статус</h4>
            <#if book.isTaken??>
                <p class="alert alert-secondary">Книга на руках у сотрудника <strong>${book.isTaken.name}</strong>.</p>
            <#else>
                <p class="alert alert-success">Книга свободна и находится в библиотеке.</p>
            </#if>

            <#if user??>
                <div class="mt-4">
                    <#if book.isTaken??>
                        <#if book.isTaken.email == user.email>
                            <a href="/return?id=${book.id}&redirect=/book?id=${book.id}" class="btn btn-danger">Вернуть
                                книгу</a>
                        </#if>
                    <#else>
                        <a href="/checkout?id=${book.id}&redirect=/book?id=${book.id}" class="btn btn-primary">Взять
                            книгу</a>
                    </#if>
                </div>
            </#if>
        </div>
    </div>
<#else>
    <div class="alert alert-danger text-center">
        <h1>Книга не найдена</h1>
    </div>
</#if>

<div class="mt-4">
    <a href="/books">« Вернуться к списку книг</a>
</div>
</@layout.page>
