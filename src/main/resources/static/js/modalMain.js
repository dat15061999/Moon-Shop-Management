const page = {
    url: {
        getAllProducts: "http://localhost:8081/api/products",
        getProductById: "http://localhost:8081/api/products/"
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
page.elements.modalCart = $('#modalCart');


let productID = 0;

let cartProducts = [];

async function fetchALlProduct() {
    return await $.ajax({
        url: page.url.getAllProducts
    })
}

page.commands.getAllProduct = async () => {
    const persons = await fetchALlProduct();

    persons.forEach(item => {
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

page.commands.handleClickSearch = () => {
    $('button.search').on('click', function () {
        productID = $(this).attr('id').replace('tr_', '');

        page.commands.handleClickButtonSearch(productID);
    })
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
page.elements.btnOpenCart.on('click', () => {
    page.elements.modalCart.modal('show');
})

// Add product to cart
page.elements.btnAddProductToCart.on('click', async () => {
    const idCustomer = 1;

    const idProduct = page.elements.idProduct.val()
    const product = await $.ajax({
        url: page.url.getProductById + idProduct
    })
    const namePr = product.productName;
    const productPrice = product.productPrice;
    const imagePr = product.imageList[0].url;

    const size = page.commands.getSizeOrColor(page.elements.getSize);
    const color = page.commands.getSizeOrColor(page.elements.getColor);
    const totalAmount = parseFloat(page.elements.totalAmount.text().replace('$', ''));
    const amountIn = parseFloat(page.elements.amountIn.val());

    const data = {
        idProduct,
        namePr,
        productPrice,
        imagePr,
        size,
        color,
        totalAmount,
        amountIn
    }

    if (cartProducts !== null) {
        let productFound = false;
        for (let i = 0; i < cartProducts.length; i++) {
            if (cartProducts[i].idProduct === idProduct) {
                cartProducts[i].amountIn = cartProducts[i].amountIn + amountIn;
                cartProducts[i].totalAmount = parseFloat(cartProducts[i].totalAmount + totalAmount);
                productFound = true;
                break;
            }
        }
        if (!productFound) {
            cartProducts.push(data);
        }
    } else {
        cartProducts = [data];
    }


    page.commands.addLocalStorage("ID"+idCustomer,cartProducts);


    page.commands.removeAllSizeOrColor(page.elements.getSize);

    page.commands.removeAllSizeOrColor(page.elements.getColor);

    page.elements.modalProductDetail.modal('hide');

    alert("Thanh cong");
    console.log(cartProducts)
})

// add cart
page.commands.addLocalStorage = (idCustomer,data) => {
    localStorage.setItem(idCustomer,JSON.stringify(data));
}
// get cart
page.commands.parseLocalStorage = (idCustomer)=> {
    return JSON.parse(localStorage.getItem("ID"+idCustomer));
}



//get size or color product
page.commands.getSizeOrColor = function (element) {
    var selectedValue ;
    for (var i = 0; i < element.length; i++) {
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

page.elements.removeAllSizeOrColor = (item)=> {
    for (let i = 0 ; i < item.length; i++) {
        if (item[i].checked()) {
            item[i].prop('check', false);
        }
    }
}



$(async () => {
    await page.commands.getAllProduct();
    page.commands.handleClickSearch();
    cartProducts = page.commands.parseLocalStorage(1);
})