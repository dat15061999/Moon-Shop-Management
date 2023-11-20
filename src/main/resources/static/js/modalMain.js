const page = {
    url: {
        getAllProducts: AppUtils.BASE_PRODUCT_API,
        getProductById: AppUtils.BASE_PRODUCT_API + "/",
        addProductToCart: AppUtils.BASE_ADD_PRO_TO_CART_API + "/",
        getAllCartList: AppUtils.BASE_All_CARTS_API + "/",
        deleteProductFromCart: AppUtils.BASE_DELETE_PRODUCT_FROM_CART_API,
        countCartDetails: AppUtils.BASE_COUNT_CART_API
    },
    elements: {},
    loadData: {},
    commands: {}
}

page.elements.renderProduct = $('#render');
page.elements.modalProductDetail = $('#modalPro');

//Modal product
page.elements.formProduct = $('#frmProduct');
page.elements.nameProduct = $('#namePr');
page.elements.viewProduct = $('#viewsPr');
page.elements.priceProduct = $('#pricePr');
page.elements.descriptionProduct = $('#descriptionPr');
page.elements.imageProduct = $('#imagePr');
page.elements.plus = $('.btn-plus');
page.elements.minus = $('.btn-minus');
page.elements.totalAmount = $('#totalAmount');
page.elements.idProduct = $('#idPr');
page.elements.btnAddProductToCart = $('#btnProduct');
page.elements.getSize = $('.size')
page.elements.getColor = $('.color')
page.elements.amountIn = $('.amountClothes');

//Modal cart
page.elements.btnOpenCart = $('.btn-cart');
page.elements.renderListCart = $('.renderCart');
page.elements.modalCart = $('#modalCart');
page.elements.plusCart = $('.btn-plus-cart');
page.elements.minusCart = $('.btn-minus-cart');
page.elements.countCartDetails = $('#show-count-cart-detail');
page.elements.btnDeleteProFromCart = $('button.btn-delete');
page.elements.renderCartOnCheckout = $('.renderCarts');



let customerID = 1;

let productID = 0;

let cartProducts = [];

async function fetchALlProduct() {
    return await $.ajax({
        url: page.url.getAllProducts
    })
}

page.commands.getAllProduct = async () => {
    const products = await fetchALlProduct();

    products.forEach(item => {
        const str = page.commands.render(item)

        page.elements.renderProduct.prepend(str);
    });
}

page.commands.render = (obj) => {
    return `
    <div class="col-lg-4 col-md-6 col-sm-6 pb-1" >
                    <div class="product-item bg-light mb-4">
                        <div class="product-img position-relative overflow-hidden">
                            <img class="img-fluid w-100" src="/img/${obj.imageList[0].url}" alt="">
                            <div class="product-action">
                                <a class="btn btn-outline-dark btn-square" href=""><i
                                        class="fa fa-shopping-cart"></i></a>
                                <a class="btn btn-outline-dark btn-square" href=""><i class="far fa-heart"></i></a>
                                <a class="btn btn-outline-dark btn-square" href=""><i
                                        class="fa fa-sync-alt"></i></a>
                                <button class="btn btn-outline-dark btn-square search" id="tr_${obj.id}"    ><i
                                        class="fa fa-search"></i></button>
                            </div>
                        </div>
                        <div class="text-center py-4">
                            <a class="h6 text-decoration-none text-truncate" href="">${obj.productName}</a>
                            <div class="d-flex align-items-center justify-content-center mt-2">
                                <h5>${obj.productPrice}$</h5>
                                <h6 class="text-muted ml-2">
                                    <del>$999.00</del>
                                </h6>
                            </div>
                            <div class="d-flex align-items-center justify-content-center mb-1">
                                <small class="fa fa-star text-primary mr-1"></small>
                                <small class="fa fa-star text-primary mr-1"></small>
                                <small class="fa fa-star text-primary mr-1"></small>
                                <small class="fa fa-star text-primary mr-1"></small>
                                <small class="fa fa-star text-primary mr-1"></small>
                                <small>(99)</small>
                            </div>
                        </div>
                    </div>
                </div>
    `;
}


page.elements.minus.on('click', function () {
    page.commands.changeClickPlusOrMinus();
})
page.elements.plus.on('click', function () {
    page.commands.changeClickPlusOrMinus();
})

page.commands.changeClickPlusOrMinus = () => {
    const price = parseFloat(page.elements.priceProduct.text());
    const amount = parseFloat(page.elements.amountIn.val());
    const newPrice = price * amount;
    page.elements.totalAmount.text(newPrice + "$");
}

page.commands.handleClick = async () => {
    $('button.search').on('click', function () {
        productID = $(this).attr('id').replace('tr_', '');

        page.commands.handleClickButtonSearch(productID);
    })
}


//Render list Cart
page.commands.renderCart = (obj) => {
    return `
    <tr id="cd_${obj.idCartDetail}">
             <td class="align-middle"><img src="/img/${obj.url}" alt="" style="width: 50px;">
                 ${obj.productName}  (${obj.sizePro}) (${obj.colorPro})
             </td>
             <td class="align-middle">${obj.productPrice}$</td>
             <td class="align-middle">
                 <div class="input-group quantity mx-auto" style="width: 100px;">
                     <div class="input-group-btn">
                         <button class="btn btn-sm btn-primary btn-minus" type="button">
                             <i class="fa fa-minus"></i>
                         </button>
                     </div>
                     <input type="text"
                            class="form-control form-control-sm bg-secondary border-0 text-center"
                            value="${obj.quantity}" readonly>
                     <div class="input-group-btn">
                         <button class="btn btn-sm btn-primary btn-plus" type="button">
                             <i class="fa fa-plus"></i>
                         </button>
                     </div>
                 </div>
             </td>
             <td class="align-middle totalAmountProduct">${obj.totalAmount}$</td>
             <td class="align-middle">
                 <button class="btn btn-sm btn-danger btn-delete-product" type="button"><i class="fa fa-times"></i></button>
             </td>
    </tr>
    `
        ;
}

// Modal product detail
page.commands.handleClickButtonSearch = (productID) => {
    $.ajax({
        url: page.url.getProductById + productID,
    })
        .done(async (data) => {
            page.elements.nameProduct.text(data.productName)
            // page.elements.viewProduct
            page.elements.priceProduct.text(data.productPrice + "$")
            page.elements.totalAmount.text(data.productPrice + "$")
            page.elements.descriptionProduct.text(data.description)
            page.elements.imageProduct.attr('src', "/img/" + data.imageList[0].url)
            page.elements.amountIn.val(1);
            page.elements.idProduct.val(data.id)

            page.elements.modalProductDetail.modal('show');
        })
}

// Modal cart detail
page.elements.btnOpenCart.on('click', async () => {

    const customerID = 1;

    await page.commands.renderListProductsToCart(customerID);

    page.commands.amountProsOnCart();

})
page.commands.amountProsOnCart = ()=> {
    let amount = 0;

    $('.totalAmountProduct').each(function() {
        amount += parseFloat($(this).text().replace('$', ''));
    });

    $('#subtotal').text(amount+"$");
    $('#total').text(amount+10+"$");

}
//render checkout
page.commands.renderCartToBillCheckout = async ()=>{

    const str = '<h6 class="mb-3">Products</h6>';

    const listCart = await $.ajax({
        url: page.url.getAllCartList + customerID,
        method: "GET"
    });
    page.elements.renderCartOnCheckout.prepend(str)

    listCart.forEach(item => {
        const str = page.commands.renderCartToBill(item);

        page.elements.renderCartOnCheckout.prepend(str);

    });

    $('#subtotal').text();
    $('#total').text();


}
page.commands.renderCartToBill= (item)=> {
    return `
     <div class="d-flex justify-content-between">
         <p>${item.productName}</p>
         <p>${item.amount}$</p>
     </div>
    `
    ;
}

page.commands.renderListProductsToCart = async (customerId)=> {
    page.elements.renderListCart.empty();

    let amount = 0;

    const listCart = await $.ajax({
        url: page.url.getAllCartList + customerId,
        method: "GET"
    });

    listCart.forEach(item => {
        const str = page.commands.renderCart(item);

        page.elements.renderListCart.prepend(str);

    });

    await page.commands.handleClickBtnDelete();

    page.commands.amountProsOnCart();

    page.elements.modalCart.modal('show');
}

page.commands.handleClickBtnDelete = async () => {

    $('.btn-delete-product').on('click', async function () {

        const cartDetailID = $(this).closest('tr').attr('id').replace('cd_', '');

        await page.commands.deleteProFromCart(cartDetailID);
    })
}

page.commands.deleteProFromCart = async (cartDetailID) => {

    await $.ajax({
        url: page.url.deleteProductFromCart + cartDetailID}
    )
    await page.commands.countCartDetailByCustomerID(customerID)

    await page.commands.renderListProductsToCart(1);
}


// Add product to cart
page.elements.btnAddProductToCart.on('click', async () => {

    const idCustomer = 1;

    const idProduct = page.elements.idProduct.val()

    const size = page.commands.getSizeOrColor(page.elements.getSize);

    const color = page.commands.getSizeOrColor(page.elements.getColor);

    const totalAmount = parseFloat(page.elements.totalAmount.text().replace('$', ''));

    const amountIn = parseFloat(page.elements.amountIn.val());

    const data = {
        idProduct,
        idCustomer,
        sizePro: size,
        colorPro: color,
        amount: amountIn,
        totalAmount
    }

    await page.commands.addCart(data, idCustomer);

    page.commands.removeAllSizeOrColor(page.elements.getSize);

    page.commands.removeAllSizeOrColor(page.elements.getColor);

    await page.commands.countCartDetailByCustomerID(idCustomer)

    page.elements.modalProductDetail.modal('hide');

    AppUtils.showSuccess("Them vao gio hang thanh cong");

    console.log(cartProducts)
})

// add cart
page.commands.addCart = async (data, idCustomer) => {
    await $.ajax({
        url: page.url.addProductToCart + idCustomer,
        contentType: "application/json",
        data: JSON.stringify(data),
        method: "POST"
    })
}

page.commands.countCartDetailByCustomerID = async (customerID) =>{
    const count = await $.ajax({
        url: page.url.countCartDetails + customerID
    })
    page.elements.countCartDetails.text(count);
}

//get size or color product
page.commands.getSizeOrColor = function (element) {
    let selectedValue;
    for (let i = 0; i < element.length; i++) {
        if (element[i].checked) {
            selectedValue = $('label[for="' + element[i].id + '"]').text();
        }
    }
    if (selectedValue) {
        return selectedValue;
    } else if (element.is(page.elements.getSize)) {
        return "M";
    } else if (element.is(page.elements.getColor)) {
        return "Black";
    }
}

page.commands.removeAllSizeOrColor = (item) => {
    for (let i = 0; i < item.length; i++) {
        if (item[i].checked) {
            item[i].checked = false;
        }
    }
}


$(async () => {
    await page.commands.getAllProduct();
    await page.commands.handleClick();
    await page.commands.countCartDetailByCustomerID(customerID)
})