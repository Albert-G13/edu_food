<#import "../layout.ftlh" as main>
<@main.layout>
    <h2>Ваш заказ</h2>

    <#if basketItems?size == 0>
        <div class="alert alert-info">Корзина пуста. Пора что-нибудь <a href="/restaurants">заказать</a>!</div>
    <#else>
        <table class="table align-middle">
            <thead>
            <tr>
                <th>Блюдо</th>
                <th>Цена</th>
                <th class="text-center">Количество</th>
                <th>Итого</th>
            </tr>
            </thead>
            <tbody>
            <#assign total = 0>
            <#list basketItems as item>
                <tr>
                    <td>${item.dishName}</td>
                    <td>${item.price} сом</td>
                    <td class="text-center">
                        <div class="d-flex justify-content-center align-items-center">
                            <form action="/basket/update" method="post" class="me-2">
                                <#if _csrf??>
                                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                </#if>
                                <input type="hidden" name="dishId" value="${item.dishId}">
                                <input type="hidden" name="delta" value="-1">
                                <button class="btn btn-sm btn-outline-secondary">-</button>
                            </form>

                            <strong>${item.count}</strong>

                            <form action="/basket/update" method="post" class="ms-2">
                                <#if _csrf??>
                                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                </#if>
                                <input type="hidden" name="dishId" value="${item.dishId}">
                                <input type="hidden" name="delta" value="1">
                                <button class="btn btn-sm btn-outline-secondary">+</button>
                            </form>
                        </div>
                    </td>
                    <td>${item.subTotal} сом</td>
                </tr>
                <#assign total = total + item.subTotal>
            </#list>
            </tbody>
        </table>

        <div class="d-flex justify-content-between align-items-center mt-4 p-3 bg-light border rounded">
            <h4>К оплате: <strong>${total} сом</strong></h4>

            <#if SPRING_SECURITY_CONTEXT??>
                <form action="/orders/create" method="post">
                    <#if _csrf??>
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    </#if>
                    <button type="submit" class="btn btn-success btn-lg px-5">Оформить заказ</button>
                </form>
            <#else>
                <a href="/auth/login" class="btn btn-warning">Войдите, чтобы заказать</a>
            </#if>
        </div>
    </#if>
</@main.layout>