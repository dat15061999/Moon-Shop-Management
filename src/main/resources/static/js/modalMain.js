const page = {
    url: {
        // getAllProducts: AppUtils.BASE_PRODUCT_API,
        getProductById: AppUtils.BASE_PRODUCT_API + "/",
        addProductToCart: AppUtils.BASE_ADD_PRO_TO_CART_API,
        getAllCartList: AppUtils.BASE_All_CARTS_API,
        deleteProductFromCart: AppUtils.BASE_DELETE_PRODUCT_FROM_CART_API,
        countCartDetails: AppUtils.BASE_COUNT_CART_API,
        getCustomerById: AppUtils.BASE_GET_CUSTOMER_BY_ID_API,
        updateCustomerById: AppUtils.BASE_UPDATE_CUSTOMER_BY_ID_API,
        createBillFromCart: AppUtils.BASE_CREATE_BILL,
        getAllBill: AppUtils.BASE_ALL_BILL,
        getBill: AppUtils.BASE_GET_BILL_BY_ID,
        changeQuantityProOnCart: AppUtils.BASE_CHANGE_QUANTITY_CART_DETAIL_API,
    },
    elements: {},
    loadData: {},
    commands: {}
}


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


page.elements.btnDeleteProFromCart = $('button.btn-delete');
page.elements.btnOpenCheckout = $('#btn-open-checkout');

//Modal Customer
page.elements.modalCustomer = $('#modalCustomer');
page.elements.btnCustomer = $('.btn-profile');
page.elements.nameCustomer = $('#name');
page.elements.emailCustomer = $('#email');
page.elements.phoneCustomer = $('#phone');
page.elements.dobCustomer = $('#dob');
page.elements.imgCustomer = $('#imgCustomer')
page.elements.btnUpdateCustomer = $('#btn-update-customer');
page.elements.frmUpdateCustomer = $('#frmCustomer');

//Page checkout
page.elements.nameCustomerCheckout = $('#nameCheckout');
page.elements.emailCustomerCheckout = $('#emailCheckout');
page.elements.phoneCustomerCheckout = $('#phoneCheckout');
page.elements.dobCustomerCheckout = $('#dobCheckout');
page.elements.btnBill = $('#btnAddBill');
//Page Order


page.elements.btnCartToCheckout = $('#btn-to-checkout');

page.elements.frmBill = $('#frmAddToBIll');


let productID = 0;


//render bill
page.commands.renderBill = (obj) => {
    return `
                <tr id="bi_${obj.id}">
                    <td class="align-middle"><h4>ABC0${obj.id}</h4></td>
                    <td class="align-middle"><h4>${obj.date}</h4></td>
                    <td class="align-middle"><h4>${obj.total} đ</h4></td>                  
                    <td class="align-middle">
                        <button class="btn btn-sm btn-success btnShowBill"><i class="fas fa-tasks"
                                                                                   style="font-size: 20px"></i></button>
                    </td>
                </tr>
        `;
}
page.commands.renderBillPage = async () => {

    $('.renderOrders').empty();

    const listCart = await $.ajax({
        url: page.url.getAllBill,
        method: "GET"
    });

    listCart.forEach(item => {

        const str = page.commands.renderBill(item);

        $('.renderOrders').prepend(str);

    });

    handleClickBill();
}

function handleClickBill() {

    $('.btnShowBill').on('click', async function () {
        const idBill = $(this).closest('tr').attr('id').replace('bi_', '')

        await $.ajax({
            url: page.url.getBill + idBill,
            method: "GET"
        })
            .done((data) => {


                $('.renderBillDetail').empty();

                data.forEach(item => {
                    const str = page.commands.renderBillDetail(item);

                    $('.renderBillDetail').prepend(str)
                });

                $('#modalBillDetail').modal('show');

            })
    })
}

page.commands.renderBillDetail = (obj) => {
    return `
               <tr>
                <td class="align-middle"><img src=${obj.image} alt="" style="width: 50px;">                  
                </td>
                <td class="align-middle"><h5>${obj.productName}</h5></td>
                <td class="align-middle"><h5>${obj.productPrice} đ</h5></td>
                <td class="align-middle"><h5>${obj.quantity}</h5></td>
                <td class="align-middle"><h5>${obj.totalAmount} đ</h5></td>                
              </tr>
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
    page.elements.totalAmount.text(newPrice + "đ");
}

page.commands.handleClick = async () => {
    $('button.search').on('click', function () {
        productID = $(this).attr('id').replace('tr_', '');
        page.commands.handleClickButtonSearch(productID);
    })

}
page.commands.handleClickButtonSearch = (productID) => {
    $.ajax({
        url: page.url.getProductById + productID,
    })
        .done(async (data) => {
            console.log(data)
            page.elements.nameProduct.text(data.productName)
            // page.elements.viewProduct
            page.elements.priceProduct.text(data.productPrice + "đ")
            page.elements.totalAmount.text(data.productPrice + "đ")
            page.elements.descriptionProduct.text(data.description)
            page.elements.imageProduct.attr('src', data.poster.url)
            page.elements.amountIn.val(1);
            page.elements.idProduct.val(data.id)

            page.elements.modalProductDetail.modal('show');
        })
}

//Modal customer
page.elements.btnCustomer.on('click', function () {
    page.commands.renderCustomer();
})

page.commands.renderCustomer = () => {
    $.ajax({
        url: page.url.getCustomerById
    })
        .done(async (data) => {

            console.log(data)
            page.elements.imgCustomer.attr('src', data.avatar.url);

            page.elements.nameCustomer.val(data.name);

            page.elements.emailCustomer.val(data.email);

            page.elements.phoneCustomer.val(data.phone);

            page.elements.dobCustomer.val(data.dob);


        })
    page.elements.modalCustomer.modal('show');
}
// Update customer
page.elements.btnUpdateCustomer.on('click', async () => {
    // page.elements.frmUpdateCustomer.trigger('submit');


    await page.commands.updateCustomer();

})
$('#closeUser').on('click',()=>{
    page.elements.modalCustomer.modal('hide');
    document.getElementById("nameErrors").innerText="";
    document.getElementById("emailErrors").innerText="";
    document.getElementById("dobErrors").innerText="";
})
page.commands.updateCustomer = async (e) => {
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
    const response = await fetch(page.url.updateCustomerById, {

        method: 'PATCH',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    });
    if (response.ok) {
        $('#staticBackdrop').modal('hide');

        Swal.fire({
            title: 'Đang xử lý',
            text: 'Vui lòng chờ...',
            willOpen: () => {
                Swal.showLoading();
            },
            timer: 2000, // Đợi 2 giây (2000ms)
            showCancelButton: false,
            showConfirmButton: false,
            allowOutsideClick: false
        }).then(async (result) => {
            if (result.dismiss === Swal.DismissReason.timer) {
                // Sau khi đợi 2 giây, hiển thị thông báo thành công
                await Swal.fire({
                    title: 'Created',
                    text: 'Tạo thành công.',
                    icon: 'success',
                    showConfirmButton: false,
                    position: 'top-start',
                    timer: 1500 // Hiển thị thông báo thành công trong 1,5 giây (1500ms)
                });
                await   page.elements.modalCustomer.modal('hide');


            }
        });
    } else {
        const responseJSON = await response.json();
        if (responseJSON) {
            const errorFullNameElement = document.getElementById("nameErrors");
            if ("name" in responseJSON) {
                errorFullNameElement.style.display = "block";
                errorFullNameElement.innerText = responseJSON.name;
                errorFullNameElement.style.color = "red"
            }

            const productsErrorElement = document.getElementById("dobErrors");
            if ("dob" in responseJSON) {
                productsErrorElement.style.display = "block";
                productsErrorElement.innerText = responseJSON.dob;
                productsErrorElement.style.color = "red"
            }
            const emailErrorElement = document.getElementById("emailErrors");
            if ("email" in responseJSON) {
                emailErrorElement.style.display = "block";
                emailErrorElement.innerText = responseJSON.email
                emailErrorElement.style.color = "red"
            }
        }
    }

    // await $.ajax({
    //     url: page.url.updateCustomerById,
    //     method: "PATCH",
    //     contentType: "application/json",
    //     data: JSON.stringify(data),
    //     // error: function (xhr) {
    //     //     const response = JSON.parse(xhr.responseText);
    //     //     response.forEach(error => {
    //     //         $('.error_'+idCartDetail).text(error.message);
    //     //     });
    //     //
    //     //     $('.hidden-span').css('display', 'inline');
    //     // },
    // })
    //     .fail(async (data) =>{
    //        await console.log(data)
    //     })

    // await page.commands.renderCustomerToPageCheckOut();

    // AppUtils.showSuccess("Update customer successfully!");


}
//
page.elements.btnCartToCheckout.on('click', async () => {

    window.location.assign("/home/checkoutBill")

})


//Render list Cart
page.commands.renderCart = (obj) => {
    return `
    <tr id="cd_${obj.idCartDetail}">
             <td class="align-middle"><img src=${obj.url} alt="" style="width: 50px;"></td>
             <td class="align-middle">
                 ${obj.productName}
             </td>           
             <td class="align-middle" id="pricePro_${obj.idCartDetail}">${obj.productPrice} đ</td>
             <td class="align-middle">
                 <div class="input-group quantity mx-auto" style="width: 100px;">
                     <div class="input-group-btn">
                         <button class="btn btn-sm btn-primary btn-minus-cart" type="button">
                             <i class="fa fa-minus"></i>
                         </button>
                     </div>                    
                     <input type="text"
                            class="form-control form-control-sm bg-secondary border-0 text-center " id="quantityC${obj.idCartDetail}"
                            value="${obj.quantity}">                    
                     <div class="input-group-btn">
                         <button class="btn btn-sm btn-primary btn-plus-cart" type="button">
                             <i class="fa fa-plus"></i>
                         </button>
                     </div>
                    <span class="hidden-span error_${obj.idCartDetail}" style="display: none; color: red"></span>
                 </div>
             </td>
             <td class="align-middle totalAmountProduct" id="totalAmountPro_${obj.idCartDetail}">${obj.totalAmount} đ</td>
             <td class="align-middle">
                 <button class="btn btn-sm btn-danger btn-delete-product" type="button"><i class="fa fa-times"></i></button>
             </td>
    </tr>
    `
        ;
}


page.commands.handleClickCart = () => {

    page.commands.onClickButtonPlusAndMinus($('.btn-plus-cart'), 'plus');

    page.commands.onClickButtonPlusAndMinus($('.btn-minus-cart'), 'minus');

}
page.commands.onClickButtonPlusAndMinus = function (btn, type) {
    btn.on('click', async function () {

        const idCart = $(this).closest('tr').attr('id').replace('cd_', '');

        const quantity = $('#quantityC' + idCart);

        console.log(quantity.val())


        let newQuantity = 0;
        if (type === 'plus') {
            newQuantity = (parseFloat(quantity.val()) + 1);
        }
        if (type === 'minus') {
            newQuantity = (parseFloat(quantity.val()) - 1);
        }
        if (newQuantity < 1) {
            $(this).prop('hide', false);
            return;
        }
        if (newQuantity > 50) {
            $(this).prop('disabled', false);
            return;
        }

        $('#quantityC' + idCart).val(newQuantity);


        await page.commands.changeTotalAmount(newQuantity, idCart);

        onChangeQuantity(newQuantity, idCart);
    })
}

//ham chung

function onChangeQuantity(quantity, cartDetailId) {

    $('#quantityC' + cartDetailId).val(quantity);

    // find id total cua product
    const pricePro = parseFloat($('#pricePro_' + cartDetailId).text());

    const newTotal = pricePro * quantity;

    $('#totalAmountPro_' + cartDetailId).text(newTotal + 'đ');
    // doi value
    page.commands.amountProsOnCart();
}


page.commands.changeTotalAmount = async (quantity, idCartDetail) => {
    const data = {
        quantity: quantity
    }
    await $.ajax({
        url: page.url.changeQuantityProOnCart + idCartDetail,
        data: JSON.stringify(data),
        method: "PATCH",
        contentType: "application/json",
        error: function (xhr) {
            const response = JSON.parse(xhr.responseText);

            response.forEach(error => {
                $('.error_' + idCartDetail).text(error.message);
            });

            $('.hidden-span').css('display', 'inline');
        }

    });
}


// Modal product detail

// Modal cart detail
page.elements.btnOpenCart.on('click', async () => {

    await page.commands.renderListProductsToCart();

    page.commands.amountProsOnCart();

})

page.commands.amountProsOnCart = () => {

    let amount = 0;

    $('.totalAmountProduct').each(function () {
        amount += parseFloat($(this).text().replace('đ', ''));
    });

    $('#subtotal').text(amount + "đ");

    $('#total').text(amount + "đ");

}
//Render Customer to page checkout
page.commands.renderCustomerToPageCheckOut = async () => {
    await $.ajax({
        url: page.url.getCustomerById,
        method: "GET"
    })
        .done((data) => {
            page.elements.nameCustomerCheckout.val(data.name);

            page.elements.emailCustomerCheckout.val(data.email);

            page.elements.phoneCustomerCheckout.val(data.phone);

            page.elements.dobCustomerCheckout.val(data.dob);

        })

}

//render checkout
page.commands.renderCartToBillCheckout = async () => {
    $('.renderCarts').empty();

    const listCart = await $.ajax({
        url: page.url.getAllCartList,
        method: "GET"
    });
    let str = '';

    let amount = 0;

    listCart.forEach(item => {
        str = page.commands.renderCartToBill(item);

        $('.renderCarts').prepend(str)

        amount += item.totalAmount;

    });
    str = '<h6 class="mb-3">Products</h6>';

    $('.renderCarts').prepend(str);

    $('#subtotalCheckout').text(amount + "đ");

    $('#totalCheckout').text(amount + "đ");

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

    const listCart = await $.ajax({
        url: page.url.getAllCartList,
        method: "GET"
    });

    listCart.forEach(item => {
        const str = page.commands.renderCart(item);

        page.elements.renderListCart.prepend(str);

    });

    await page.commands.handleClickBtnDelete();

    page.commands.amountProsOnCart();

    page.commands.handleClickCart();

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

    await page.commands.renderListProductsToCart();

    await page.commands.renderCartToBillCheckout();
}
//bill

page.elements.btnBill.on('click', async () => {
    if ($('#check').val() === 'true') {
        await page.commands.createBill();
    } else {
        AppUtils.showError("Bạn chưa xác nhận tại giỏ hàng!")
    }
})
page.commands.createBill = async () => {
    const amountProduct = await $.ajax({
        url: page.url.countCartDetails
    })
    if (amountProduct <= 0) {

        AppUtils.showError("Bạn chưa có sản phẩm!")

        return;
    }

    await $.ajax({
        url: page.url.createBillFromCart,
        method: "POST"
    })
    await page.commands.countCartDetailByCustomerID();

    await page.commands.renderCartToBillCheckout();

    AppUtils.showSuccess("Đặt hàng thành công!");

}

// Add product to cart
page.elements.btnAddProductToCart.on('click', async () => {

    const idProduct = page.elements.idProduct.val()

    const name = page.elements.nameProduct.text();

    const price = page.elements.priceProduct.text().replace('đ', '');

    const totalAmount = parseFloat(page.elements.totalAmount.text().replace('', ''));

    const amountIn = parseFloat(page.elements.amountIn.val());

    const data = {
        idProduct,
        productName: name,
        productPrice: price,
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
        url: page.url.addProductToCart,
        contentType: "application/json",
        data: JSON.stringify(data),
        method: "POST"
    })
}

page.commands.countCartDetailByCustomerID = async () => {
    const count = await $.ajax({
        url: page.url.countCartDetails
    })
    $('#show-count-cart-detail').text(count);
}

