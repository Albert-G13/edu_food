<#import "../layout.ftlh" as main>
<@main.layout>
    <div class="row">
        <div class="col-md-4">
            <div class="card shadow-sm mb-4">
                <div class="card-body text-center">
                    <h4>${user.name}</h4>
                    <hr>
                    <p class="text-start"><strong>Email:</strong> ${user.email}</p>
                </div>
            </div>
        </div>

        <div class="col-md-8">
            <h3 class="mb-4">История ваших заказов</h3>
            <#if orders?has_content>
                <div class="list-group shadow-sm">
                    <#list orders as order>
                        <div class="list-group-item p-3 mb-2 border rounded">
                            <div class="d-flex justify-content-between align-items-center mb-3">
                                <div>
                                    <h5 class="mb-0"><strong>${order.restaurantName}</strong></h5>
                                    <small class="text-muted">${order.date}</small>
                                </div>
                                <span class="badge bg-primary fs-6">${order.totalAmount} сом</span>
                            </div>

                            <ul class="list-group list-group-flush border-top">
                                <#list order.items as item>
                                    <li class="list-group-item d-flex justify-content-between px-0">
                                        <span>${item.dishName} x${item.count}</span>
                                        <span class="text-muted">${item.price * item.count} сом</span>
                                    </li>
                                </#list>
                            </ul>

                            <div class="mt-2 text-end">
                                <small class="text-muted">Статус: <strong class="text-success">${order.status}</strong></small>
                            </div>
                        </div>
                    </#list>
                </div>
            <#else>
                <div class="alert alert-light text-center border">
                    У вас пока нет заказов. <a href="/restaurants">Заказать еду?</a>
                </div>
            </#if>
        </div>
    </div>
</@main.layout>