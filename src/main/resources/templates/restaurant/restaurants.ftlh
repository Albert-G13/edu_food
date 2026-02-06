<#import "../layout.ftlh" as main>
<@main.layout>
    <h2 class="mb-4">Наши заведения</h2>

    <form action="/restaurants" method="get" class="mb-4">
        <div class="input-group">
            <input type="text" name="search" class="form-control"
                   placeholder="Поиск ресторана..." value="${search!''}">
            <button class="btn btn-primary" type="submit">Найти</button>
        </div>
    </form>

    <div class="row row-cols-1 row-cols-md-3 g-4">
        <#list restaurants.content as restaurant>
            <div class="col" style="width: 100%">
                <div class="card h-100 shadow-sm" style="width: 100%">
                    <div class="card-body">
                        <h5 class="card-title">${restaurant.name}</h5>
                        <p class="card-text text-muted">Лучшая кухня в городе</p>
                        <a href="/restaurants/${restaurant.id}" class="btn btn-outline-primary">Перейти к меню</a>
                    </div>
                </div>
            </div>
        </#list>
    </div>

    <div class="text-center mt-4 mb-5">
        <#if !restaurants.last>
            <a href="/restaurants?size=${restaurants.size + 5}" class="btn btn-outline-success px-5 py-2">
                Показать еще заведения
            </a>
        <#else>
            <p class="text-muted">Вы просмотрели все доступные заведения</p>
        </#if>
    </div>
</@main.layout>