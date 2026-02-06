<#import "../layout.ftlh" as main>
<@main.layout>
    <h2 class="mb-4">Мои заказы</h2>

    <#if orders?size == 0>
        <div class="alert alert-light border shadow-sm">
            Вы еще ничего не заказывали. <a href="/restaurants">Самое время начать!</a>
        </div>
    <#else>
        <div class="accordion shadow-sm" id="ordersAccordion">
            <#list orders as order>
                <div class="accordion-item">
                    <h2 class="accordion-header" id="heading${order.id}">
                        <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#collapse${order.id}">
                            <div class="d-flex justify-content-between w-100 me-3">
                                <span><strong>${order.restaurantName}</strong> — ${order.date}</span>
                                <span class="badge bg-primary">${order.totalAmount} сом</span>
                            </div>
                        </button>
                    </h2>
                    <div id="collapse${order.id}" class="accordion-collapse collapse" data-bs-parent="#ordersAccordion">
                        <div class="accordion-body">
                            <ul class="list-group list-group-flush">
                                <#list order.items as item>
                                    <li class="list-group-item d-flex justify-content-between">
                                        <span>${item.dishName} x${item.count}</span>
                                        <span class="text-muted">${item.price * item.count} сом</span>
                                    </li>
                                </#list>
                            </ul>
                            <div class="mt-3 text-end">
                                <span class="text-muted">Статус: <strong>${order.status}</strong></span>
                            </div>
                        </div>
                    </div>
                </div>
            </#list>
        </div>
    </#if>
</@main.layout>