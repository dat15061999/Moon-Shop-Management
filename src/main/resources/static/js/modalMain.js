

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
page.elements.modalCart = $('#modalPro');

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
page.elements.btnAddProductToCart = $('#btnProduct');

page.elements.amountIn = $('.amountClothes');

//Modal cart
page.elements.btnOpenCart = $('.btn-cart');
page.elements.modalCart = $('#modalCart');


let productID = 0;
let cartProducts = {};

async function fetchALlProduct() {
    return await $.ajax({
        url: page.url.getAllProducts
    })
}

page.commands.getAllProduct = async () => {
    const persons = await fetchALlProduct();
    console.log(persons)
    persons.forEach(item => {
        const str = page.commands.render(item)

        page.elements.renderProduct.prepend(str);
    });
}

page.commands.render= (obj)=> {
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


page.elements.minus.on('click',function ()  {
    page.commands.changeClickPlusOrMinus ();
})
page.elements.plus.on('click',function ()  {
    page.commands.changeClickPlusOrMinus ();
})

page.commands.changeClickPlusOrMinus = ()=>{
    const price = parseFloat(page.elements.priceProduct.text());
    const amount = parseFloat(page.elements.amountIn.val());
    const newPrice = price * amount;
    page.elements.totalAmount.text(newPrice + "$");
}

page.commands.handleClickSearch= ()=> {
    $('button.search').on('click', function (){
        productID = $(this).attr('id').replace('tr_','');

        page.commands.handleClickButtonSearch(productID);
    })
}
// Modal product detail
page.commands.handleClickButtonSearch = (productID)=> {
    $.ajax({
        url: page.url.getProductById + productID,
    })
        .done(async (data) => {
            page.elements.nameProduct.text(data.productName)
            // page.elements.viewProduct
            page.elements.priceProduct.text(data.productPrice + "$")
            page.elements.totalAmount.text(data.productPrice + "$")
            page.elements.descriptionProduct.text(data.description)
            page.elements.imageProduct.attr('src',"/img/" +data.imageList[0].url)
            page.elements.amountIn.val(1);
            page.elements.modalCart.modal('show');
        })
        .fail((err) => {

        })
}

// Modal cart detail
page.elements.btnOpenCart.on('click', ()=> {
    page.elements.modalCart.modal('show');
})

page.elements.btnAddProductToCart.on('click',  ()=>{
    cartProducts += {


    }
})
page.commands.
$(async () => {
    await page.commands.getAllProduct();
    page.commands.handleClickSearch();
})