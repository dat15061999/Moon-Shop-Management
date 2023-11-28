let pageable = {
    page: 1,
    sortService: 'id,desc',
    search: '',
    min: 1,
    max: 50000000000000,
}

async function fetchALlProduct() {
    return $.ajax({
        url: `http://localhost:8081/api/products?page=${pageable.page - 1 || 0}&sort=${pageable.sortService || 'id,desc'}&search=${pageable.search || ''}&min=${pageable.min || ''}&max=${pageable.max || ''}`
    });
}

const renderProduct = document.getElementById("render")

function render(obj) {
    return `
                <div class="col-lg-4 col-md-6 col-sm-6 pb-1" >
                        <div class="product-item bg-light mb-4">
                            <div class="product-img position-relative overflow-hidden" >
                                <img class="img-fluid w-100" src=${obj.poster.url} style="width: 280px;height: 280px" alt="">
                                <div class="product-action">                                    
                                    <button class="btn btn-outline-dark btn-square search" id="tr_${obj.id}"    ><i
                                            class="fa fa-search"></i></button>
                                </div>
                            </div>
                            <div class="text-center py-4">
                                <a class="h6 text-decoration-none text-truncate" href="">${obj.productName}</a>
                                <div class="d-flex align-items-center justify-content-center mt-2">
                                    <h5>${obj.productPrice} đ</h5>
                                    <h6 class="text-muted ml-2">
                                        <del>999.999 đ</del>
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

async function getAllProduct() {
    const products = await fetchALlProduct();
    pageable = {
        ...pageable,
        ...products
    };
    genderPagination();
    let str = '';

    products.content.forEach(item => {
        str += render(item)

    });
    renderProduct.innerHTML = str
}

const ePriceRange1 = document.getElementById("priceRange1");
const ePriceRange2 = document.getElementById("priceRange2");
const ePriceRange3 = document.getElementById("priceRange3");
const ePriceRange4 = document.getElementById("priceRange4");
const ePriceRange5 = document.getElementById("priceRange5");
ePriceRange1.addEventListener('change', function () {
    searchPrice(this.value);
});
ePriceRange2.addEventListener('change', function () {
    searchPrice(this.value);
});
ePriceRange3.addEventListener('change', function () {
    searchPrice(this.value);
});
ePriceRange4.addEventListener('change', function () {
    searchPrice(this.value);
});
ePriceRange5.addEventListener('change', function () {
    searchPrice(this.value);
});

async function searchPrice(priceRange) {
    const [min, max] = priceRange.split('-').map(Number);
    await searchByPrice(min, max);
    await getAllProduct();
}


async function searchByPrice(min, max) {
    const minPrice = (min);
    const maxPrice = parseFloat(max);
    pageable.min = minPrice;
    pageable.max = maxPrice;
    await getAllProduct();
}

const paginationProduct = document.getElementById('paginationProduct')
const genderPagination = () => {
    paginationProduct.innerHTML = '';
    let str = '';
    const maxPagesToShow = 3;
    const pagesToLeft = Math.floor(maxPagesToShow / 2);
    let startPage = pageable.page - pagesToLeft;
    if (startPage < 1) {
        startPage = 1;
    }
    let endPage = startPage + maxPagesToShow - 1;
    if (endPage > pageable.totalPages) {
        endPage = pageable.totalPages;
        startPage = Math.max(1, endPage - maxPagesToShow + 1); // Đảm bảo rằng số lượng trang được hiển thị không vượt quá totalPages
    }

    // Generate "Previous" button
    str += `<li class="page-item ${pageable.first ? 'disabled' : ''}">
              <a class="page-link" href="#" tabindex="-1" aria-disabled="true">Previous</a>
            </li>`

    // Generate page numbers
    for (let i = startPage; i <= endPage; i++) {
        str += `<li class="page-item ${(pageable.page) === i ? 'active' : ''}" id="${i}" aria-current="page">
                    <a class="page-link" href="#">${i}</a>
                </li>`
    }

    // Generate "Next" button
    str += `<li class="page-item ${pageable.last ? 'disabled' : ''}">
              <a class="page-link" href="#" tabindex="-1" aria-disabled="true">Next</a>
            </li>`

    paginationProduct.innerHTML = str;

    const ePages = paginationProduct.querySelectorAll('li');
    const ePrevious = ePages[0];
    const eNext = ePages[ePages.length - 1];

    ePrevious.onclick = async () => {
        if (pageable.page === 1) {
            return;
        }
        pageable.page -= 1;
        await getAllProduct();
    }

    eNext.onclick = async () => {
        if (pageable.page === pageable.totalPages) {
            return;
        }
        pageable.page += 1;
        await getAllProduct();
    }
    for (let i = 1; i < ePages.length - 1; i++) {
        const currentPageId = ePages[i].id;

        if (currentPageId === pageable.page) {
            continue;
        }
        ePages[i].onclick = async () => {
            pageable.page = parseInt(currentPageId, 10); // Convert id to integer
            await getAllProduct();
            await handle();
        };
    }
}

$(async () => {
    if ($('#shop').val() === 'true') {
        await getAllProduct();
    }
        await handle();
    }
);

async function handle() {
    await page.commands.handleClick();
    await page.commands.countCartDetailByCustomerID();
    await page.commands.renderCustomerToPageCheckOut();
    //await page.commands.renderBillPage();
    if ($('#check').val() === 'true') {
        await page.commands.renderCartToBillCheckout();
    }

}