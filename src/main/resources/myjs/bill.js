var eMenubar = document.getElementsByClassName("menu-item");
const billForm = document.getElementById('billForm');
const tBodyBill = document.getElementById("tBodyBill")
const eSearch = document.getElementById('search')

const emailInput = document.getElementById("email");
const customerNameInput = document.getElementById("customerName");
const customerPhoneInput = document.getElementById("customerPhone");
const customerEmailInput = document.getElementById("customerEmail");
const customerQuantityInput = document.getElementById("customerQuantity");
const usersInput = document.getElementById("users");
const productsInput = document.getElementById("products");
const combosInput = document.getElementById("combos");
const appointmentTimeInput = document.getElementById("appointmentTime");
const priceBillInput = document.getElementById("priceBill");

const customerEmailError = document.getElementById("customerEmailError");
const customerNameError = document.getElementById("customerNameError");
const customerPhoneError = document.getElementById("customerPhoneError");
const customerQuantityError = document.getElementById("customerQuantityError");
const usersError = document.getElementById("usersError");
const productsError = document.getElementById("productsError");
const combosError = document.getElementById("combosError");
const appointmentTimeError = document.getElementById("appointmentTimeError");
const priceBillError = document.getElementById("priceBillError");
const saveButton = document.getElementById("save");
const paginationBill = document.getElementById('paginationBill')

let bills = [];
let billSelected = {};
for (var i = 0; i < eMenubar.length; i++) {
    eMenubar[i].classList.remove("active");
}

window.onload = async () => {
    await getList();
}
document.getElementById("menu-bill").classList.add("active");
let pageable ={
    page: 1,
    search: "",
    sortUser: "id,desc"
}
async function getList(){
    const result = await (await fetch(`/api/bills?page=${pageable.page - 1 || 0}&sort=${pageable.sortUser}&search=${pageable.search || ''}`)).json()
    pageable = {
        ...pageable,
        ...result
    };
    genderPagination();
    renderTBody(result.content);
}
const genderPagination = () => {
    paginationBill.innerHTML = '';
    let str = '';
    const maxPagesToShow = 5;
    const pagesToLeft = Math.floor(maxPagesToShow / 2);
    const pagesToRight = maxPagesToShow - pagesToLeft;
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
    str += `<li class="page-item ${pageable.startPage ? 'disabled' : ''}">
              <a class="page-link" href="#" tabindex="-1" aria-disabled="true">Previous</a>
            </li>`

    // Generate page numbers
    for (let i = startPage; i <= endPage; i++) {
        str += `<li class="page-item ${(pageable.page) === i ? 'active' : ''}" id="${i}" aria-current="page">
                    <a class="page-link" href="#">${i}</a>
                </li>`
    }

    // Generate "Next" button
    str += `<li class="page-item ${pageable.endPage ? 'disabled' : ''}">
              <a class="page-link" href="#" tabindex="-1" aria-disabled="true">Next</a>
            </li>`

    paginationBill.innerHTML = str;

    const ePages = paginationBill.querySelectorAll('li');
    const ePrevious = ePages[0];
    const eNext = ePages[ePages.length - 1];

    ePrevious.onclick = () => {
        if (pageable.page === 1) {
            return;
        }
        pageable.page -= 1;
        getList();
    }

    eNext.onclick = () => {
        if (pageable.page === pageable.totalPages) {
            return;
        }
        pageable.page += 1;
        getList();
    }
    for (let i = 1; i < ePages.length - 1; i++) {
        const currentPageId = ePages[i].id;

        if (currentPageId === pageable.page) {
            continue;
        }
        ePages[i].onclick = () => {
            pageable.page = parseInt(currentPageId, 10); // Convert id to integer
            getList();
        };
    }
}

function renderTBody(items) {
    let str = '';
    if (Array.isArray(items)) {

        items.forEach(e => {
            str += renderItemStr(e);
        });
    }
    tBodyBill.innerHTML = str;
}
let itemIdUser= 0;
function renderItemStr(item) {
    itemIdUser= item.id
    var deleteButtonStyle = (item.epayment === "PAID") ? 'display: none;' : '';
    return `<tr>
                    <td>
                        ${item.id}
                    </td>
                    <td >
                       <span onmouseover="showTooltip(this)" data-id="${item.id}" id="billDB"> ${item.customerName}</span>
                    </td>
                    <td>
                        ${item.customerQuantity}
                    </td>
                    <td>
                        ${item.appointmentTime}
                    </td>
                     <td>
                        ${formatCurrency(item.price)}
                    </td>
                    <td onclick="payment(${item.id})">
                        <div id="divPayment" style="border: ${item.epayment==="PAID" ? '1px solid #e9d9d9' : ' '};background-color: ${item.epayment==="PAID" ? '#f7c250' : ''};padding: 2px; border-radius: 12px;">
                        ${item.epayment}
                        </div>
                    </td>
                    <td style="width: 120px;" >
                        <a class="btn edit" data-id="${item.id}" onclick="onShowEdit(${item.id})" id="edit" style="padding: 0;     width: 21px;">
                            <i class="fa-regular fa-pen-to-square text-primary"></i>
                        </a>
                        <a  class="btn delete" id="deleteBill" data-id="${item.id}" onclick="deleteItem(${item.id})" id="delete" style="padding-right: 5px; width: 47px;${deleteButtonStyle}""
                            >
                            <i class="fa-regular fa-trash-can text-danger"></i>
                        </a>
                    </td>
                </tr>`
}
async function deleteItem(id){

    const { isConfirmed } = await Swal.fire({
        title: 'Xác nhận hủy đơn',
        text: 'Bạn có chắc chắn muốn hủy?',
        icon: 'warning',
        showCancelButton: true,
        confirmButtonText: 'Đồng ý',
        cancelButtonText: 'Hủy',
    });
    if (!isConfirmed) {
        return;
    }
    const response = await fetch(`/api/bills/lock/${id}`, {
        method: 'PATCH',
    });

    if (response.ok) {
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
                await   Swal.fire({
                    text: 'Hủy thành công.',
                    icon: 'success',
                    showConfirmButton: false,
                    position: 'top-start',
                    timer: 900
                })
            }
            await getList();
        });
    }
}

function formatCurrency(number) {
    return new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND' }).format(number);
}

const onSearch = (e) => {
    e.preventDefault()
    pageable.search = eSearch.value;
    pageable.page = 1;
    getList();
}

const searchInput = document.querySelector('#search');

searchInput.addEventListener('search', () => {
    onSearch(event)
});


// async function payment(id){
//     const billPayment = await findById(id);
//     console.log(billPayment)
//     if(billPayment.epayment === "UNPAID"){
//         const { isConfirmed } = await Swal.fire({
//             title: 'Xác nhận thanh toán',
//             text: 'Thanh toán?',
//             icon: 'warning',
//             showCancelButton: true,
//             confirmButtonText: 'Đồng ý',
//             cancelButtonText: 'Hủy',
//         });
//         if (!isConfirmed) {
//             return; // Người dùng đã hủy xóa
//         }
//         const response = await fetch(`/api/bills/paid/${id}`, {
//             method: 'PATCH',
//         });
//
//         if (response.ok) {
//             Swal.fire({
//                 title: 'Đang xử lý',
//                 text: 'Vui lòng chờ...',
//                 willOpen: () => {
//                     Swal.showLoading();
//                 },
//                 timer: 2000, // Đợi 2 giây (2000ms)
//                 showCancelButton: false,
//                 showConfirmButton: false,
//                 allowOutsideClick: false
//             }).then(async (result) => {
//                 if (result.dismiss === Swal.DismissReason.timer) {
//                     await   Swal.fire({
//                         text: 'Đã thanh toán.',
//                         icon: 'success',
//                         showConfirmButton: false,
//                         position: 'top-start',
//                         timer: 900
//                     })
//                 }
//                 await getList();
//             });
//         }
//     } else {
//         const response = await fetch(`/api/bills/unpaid/${id}`, {
//             method: 'PATCH',
//         });
//         const { isConfirmed } = await Swal.fire({
//             title: 'Xác nhận chưa thanh toán',
//             text: 'Hủy thanh toán ?',
//             icon: 'warning',
//             showCancelButton: true,
//             confirmButtonText: 'Đồng ý',
//             cancelButtonText: 'Hủy',
//         });
//         if (!isConfirmed) {
//             return; // Người dùng đã hủy xóa
//         }
//         if (response.ok) {
//             Swal.fire({
//                 title: 'Đang xử lý',
//                 text: 'Vui lòng chờ...',
//                 willOpen: () => {
//                     Swal.showLoading();
//                 },
//                 timer: 2000, // Đợi 2 giây (2000ms)
//                 showCancelButton: false,
//                 showConfirmButton: false,
//                 allowOutsideClick: false
//             }).then( async (result) => {
//                 if (result.dismiss === Swal.DismissReason.timer) {
//                     await    Swal.fire({
//                         text: 'Chưa thanh toán.',
//                         icon: 'success',
//                         showConfirmButton: false,
//                         position: 'top-start',
//                         timer: 900
//                     })
//                 }
//                 await getList();
//             });
//         }
//     }
// }





