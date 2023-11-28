class AppUtils {
    static BASE_API_URL = 'http://localhost:8081/api';
    static BASE_PRODUCT_API = this.BASE_API_URL + '/products';
    static BASE_ADD_PRO_TO_CART_API = this.BASE_PRODUCT_API + '/cart';
    static BASE_All_CARTS_API = this.BASE_PRODUCT_API + '/cart';
    static BASE_DELETE_PRODUCT_FROM_CART_API = this.BASE_PRODUCT_API + '/delete/';
    static BASE_COUNT_CART_API = this.BASE_PRODUCT_API + '/cartDetail';
    static BASE_CHANGE_QUANTITY_CART_DETAIL_API = this.BASE_PRODUCT_API + '/cartDetail/';
    static BASE_GET_CUSTOMER_BY_ID_API = this.BASE_PRODUCT_API + '/customer';
    static BASE_UPDATE_CUSTOMER_BY_ID_API = this.BASE_PRODUCT_API + '/customer';
    static BASE_CREATE_BILL = this.BASE_PRODUCT_API + '/bill';
    static BASE_ALL_BILL = this.BASE_PRODUCT_API + '/bill';
    static BASE_GET_BILL_BY_ID = this.BASE_PRODUCT_API + '/bill/';


    static showSuccess = (text) => {
        Swal.fire({
            title: text,
            showConfirmButton: false,
            icon: 'success',
            position: 'top-right',
        })
    }

    static showError = (text) => {
        Swal.fire({
            position: "top-right",
            icon: "error",
            title: text,
            showConfirmButton: false,
        })
    }
}