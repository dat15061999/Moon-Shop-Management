$(async () => {
    await page.commands.countCartDetailByCustomerID();
    await page.commands.renderCustomerToPageCheckOut();
    //await page.commands.renderBillPage();
    if ($('#check').val() === 'true') {
        await page.commands.renderCartToBillCheckout();
    }
    await page.commands.renderBillPage();
});