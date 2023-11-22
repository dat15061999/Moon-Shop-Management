const page = {
    url: {
        getAllProducts: AppUtils.BASE_PRODUCT_API,
        getProductById: AppUtils.BASE_PRODUCT_API + "/",
        addProductToCart: AppUtils.BASE_ADD_PRO_TO_CART_API + "/",
        getAllCartList: AppUtils.BASE_All_CARTS_API + "/",
        deleteProductFromCart: AppUtils.BASE_DELETE_PRODUCT_FROM_CART_API,
        countCartDetails: AppUtils.BASE_COUNT_CART_API,
        getCustomerById: AppUtils.BASE_GET_CUSTOMER_BY_ID_API,
        updateCustomerById: AppUtils.BASE_UPDATE_CUSTOMER_BY_ID_API,
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
page.elements.btnOpenCheckout = $('#btn-open-checkout');

//Modal Customer
page.elements.modalCustomer = $('#modalCustomer');
page.elements.btnCustomer = $('.btn-profile');
page.elements.nameCustomer = $('#name');
page.elements.emailCustomer = $('#email');
page.elements.phoneCustomer = $('#phone');
page.elements.dobCustomer = $('#dob');

page.elements.btnUpdateCustomer = $('#btn-update-customer');
page.elements.frmUpdateCustomer = $('#frmCustomer');
//Page checkout
page.elements.nameCustomerCheckout = $('#nameCheckout');
page.elements.emailCustomerCheckout = $('#emailCheckout');
page.elements.phoneCustomerCheckout = $('#phoneCheckout');
page.elements.dobCustomerCheckout = $('#dobCheckout');

page.elements.btnBill = $('#btnAddBill');

page.elements.frmBill = $('#frmAddToBIll');

let customerID = $('#customerID').val();

let productID = 0;

async function fetchALlProduct() {
    return $.ajax({
        url: page.url.getAllProducts
    });
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
                                <img class="img-fluid w-100" src="../img/${obj.imageList[0].url}" alt="">
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
//Modal customer
page.elements.btnCustomer.on('click', function () {
    page.commands.renderCustomer();
})

page.commands.renderCustomer = () => {
    $.ajax({
        url: page.url.getCustomerById + customerID
    })
        .done(async (data) => {

            page.elements.nameCustomer.val(data.name);

            page.elements.emailCustomer.val(data.email);

            page.elements.phoneCustomer.val(data.phone);

            page.elements.dobCustomer.val(data.dob);


        })
    page.elements.modalCustomer.modal('show');
}
// Update customer
page.elements.btnUpdateCustomer.on('click',async ()=>{
    // page.elements.frmUpdateCustomer.trigger('submit');
    await page.commands.updateCustomer();
})

// page.elements.frmUpdateCustomer.validate({
//     onkeyup: function (element) {
//         $(element).valid()
//     },
//     onclick: false,
//     onfocusout: false,
//     errorLabelContainer: "#modalCustomer .area-error",
//     errorPlacement: function (error, element) {
//         error.appendTo("#modalCustomer .area-error");
//     },
//     showErrors: function (errorMap, errorList) {
//         if (this.numberOfInvalids() > 0) {
//             $("#modalCustomer .area-error").removeClass("hide").addClass("show");
//         } else {
//             $("#modalCustomer .area-error").removeClass("show").addClass("hide").empty();
//
//             $("#modalCustomer input.error").removeClass("error");
//         }
//         this.defaultShowErrors();
//     },
//     submitHandler: async () => {
//        await page.commands.updateCustomer();
//     }
// })

page.commands.updateCustomer = async () => {

    const name = page.elements.nameCustomer.val();

    const email = page.elements.emailCustomer.val();

    const phone = page.elements.phoneCustomer.val();

    const dob = page.elements.dobCustomer.val();



    const data = {
        name,
        email,
        phone,
        dob,

    }
    await $.ajax({
        url: page.url.updateCustomerById + customerID,
        method: "PATCH",
        contentType: "application/json",
        data: JSON.stringify(data),
    })

    await page.commands.renderCustomerToPageCheckOut();

    AppUtils.showSuccess("Update customer successfully!");

    page.elements.modalCustomer.modal('hide');

}

//Render list Cart
page.commands.renderCart = (obj) => {
    return `
    <tr id="cd_${obj.idCartDetail}">
             <td class="align-middle"><img src="/img/${obj.url}" alt="" style="width: 50px;">
                 ${obj.productName}
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

    await page.commands.renderListProductsToCart();

    page.commands.amountProsOnCart();

})

page.commands.amountProsOnCart = () => {
    let amount = 0;

    $('.totalAmountProduct').each(function () {
        amount += parseFloat($(this).text().replace('$', ''));
    });

    $('#subtotal').text(amount + "$");

    $('#total').text(amount + "$");

}
//Render Customer to page checkout
page.commands.renderCustomerToPageCheckOut = async ()=>{
    await $.ajax({
        url: page.url.getCustomerById + customerID,
        method: "GET"
    })
        .done((data)=>{
            page.elements.nameCustomerCheckout.val(data.name);

            page.elements.emailCustomerCheckout.val(data.email);

            page.elements.phoneCustomerCheckout.val(data.phone);

            page.elements.dobCustomerCheckout.val(data.dob);

        })

}

//render checkout
page.commands.renderCartToBillCheckout = async () => {
    page.elements.renderCartOnCheckout.empty();

    const listCart = await $.ajax({
        url: page.url.getAllCartList + customerID,
        method: "GET"
    });
    let str = '';

    let amount = 0;

    listCart.forEach(item => {
        str = page.commands.renderCartToBill(item);

        page.elements.renderCartOnCheckout.prepend(str)

        amount += item.totalAmount;

    });
    str = '<h6 class="mb-3">Products</h6>';

    page.elements.renderCartOnCheckout.prepend(str);

    $('#subtotalCheckout').text(amount + "$");

    $('#totalCheckout').text(amount  + "$");

}

page.commands.renderCartToBill = (item) => {
    return `
     <div class="d-flex justify-content-between">
         <p>${item.productName}</p>
         <p>${item.totalAmount}$</p>
     </div>
    `
        ;
}


page.commands.renderListProductsToCart = async () => {
    page.elements.renderListCart.empty();

    let amount = 0;

    const listCart = await $.ajax({
        url: page.url.getAllCartList + customerID,
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
            url: page.url.deleteProductFromCart + cartDetailID
        }
    )
    await page.commands.countCartDetailByCustomerID()

    await page.commands.renderListProductsToCart(1);

    await page.commands.renderCartToBillCheckout();
}
//bill
page.elements.btnBill.on('click', async () => {
    page.elements.frmBill.trigger('submit')
})

// Add product to cart
page.elements.btnAddProductToCart.on('click', async () => {

    const idProduct = page.elements.idProduct.val()

    const name = page.elements.nameProduct.text();

    const price = page.elements.priceProduct.text().replace('$','');

    const totalAmount = parseFloat(page.elements.totalAmount.text().replace('$', ''));

    const amountIn = parseFloat(page.elements.amountIn.val());

    const data = {
        idProduct,
        customerID,
        productName:name,
        productPrice:price,
        amount: amountIn,
        totalAmount
    }

    await page.commands.addCart(data);

    await page.commands.countCartDetailByCustomerID()

    page.elements.modalProductDetail.modal('hide');

    AppUtils.showSuccess("Them vao gio hang thanh cong");


})

// add cart
page.commands.addCart = async (data) => {
    await $.ajax({
        url: page.url.addProductToCart + customerID,
        contentType: "application/json",
        data: JSON.stringify(data),
        method: "POST"
    })
}

page.commands.countCartDetailByCustomerID = async () => {
    const count = await $.ajax({
        url: page.url.countCartDetails + customerID
    })
    page.elements.countCartDetails.text(count);
}

$(async () => {
    await page.commands.getAllProduct();
    await page.commands.handleClick();
    await page.commands.countCartDetailByCustomerID();
    await page.commands.renderCartToBillCheckout();
    await page.commands.renderCustomerToPageCheckOut();
})