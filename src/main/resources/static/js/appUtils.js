class AppUtils {
    static BASE_API_URL = 'http://localhost:8081/api';
    static BASE_PRODUCT_API = this.BASE_API_URL + '/products';
    static BASE_ADD_PRO_TO_CART_API = this.BASE_PRODUCT_API + '/cart';
    static BASE_All_CARTS_API = this.BASE_PRODUCT_API + '/cart';
    static BASE_DELETE_PRODUCT_FROM_CART_API = this.BASE_PRODUCT_API + '/delete/';
    static BASE_COUNT_CART_API = this.BASE_PRODUCT_API + '/cartDetail/';

    static showSuccess = (text) => {
        Swal.fire({
            heading: 'Success',
            text: text,
            showHideTransition: 'slide',
            icon: 'success',
            position: 'top-right',
        })
    }

    static showError = (text) => {
        Swal.fire({
            position: "top-end",
            icon: "error",
            title: "Your work has been saved",
            showConfirmButton: false,
            timer: 1500
        })
    }
}