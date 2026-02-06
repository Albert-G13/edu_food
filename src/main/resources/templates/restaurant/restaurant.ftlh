<#import "../layout.ftlh" as main>
<@main.layout>
    <nav aria-label="breadcrumb">
        <ol class="breadcrumb">
            <li class="breadcrumb-item"><a href="/restaurants">Все рестораны</a></li>
            <li class="breadcrumb-item active">${restaurant.name}</li>
        </ol>
    </nav>

    <h1 class="display-4 mb-4">${restaurant.name}</h1>

    <div class="list-group shadow-sm">
        <#list dishes.content as dish>
            <div class="list-group-item d-flex justify-content-between align-items-center p-3">
                <div>
                    <h5 class="mb-1">${dish.name}</h5>
                    <span class="badge bg-success rounded-pill">${dish.price} сом</span>
                </div>

                <form action="/basket/add" method="post" class="m-0">
                    <#if _csrf??>
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    </#if>
                    <input type="hidden" name="dishId" value="${dish.id}">
                    <button type="submit" class="btn btn-primary btn-sm">В корзину</button>
                </form>
            </div>
        </#list>
    </div>

    <div class="text-center mt-4 mb-5">
        <#if !dishes.last>
            <a href="/restaurants/${restaurant.id}?size=${dishes.size + 10}"
               class="btn btn-outline-success px-5">
                Показать ещё блюда
            </a>
        <#else>
            <p class="text-muted">Это все блюда меню</p>
        </#if>
    </div>
</@main.layout>