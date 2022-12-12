/*
 * Copyright 2022 Ideas2IT Technologies. All rights reserved.
 * IDEAS2IT PROPRIETARY/CONFIDENTIAL.
 */
package com.ideas2it.ideameds.util;

/**
 * Constant class for String message
 *
 * @author Parthasarathy Elumalai
 * @version 1.0
 * @since 2022-11-22
 */
public class Constants {
    /**
     * Share the user not found message
     */
    public static final String USER_NOT_FOUND = "User not found";

    /**
     * Share the user not Add message
     */
    public static final String USER_NOT_ADDED = "User not registered";
    /**
     * Share the Prescription has expired message
     */
    public static final String PRESCRIPTION_EXPIRED = "Prescription has Expired";

    /**
     * Share the Prescription not found message
     */
    public static final String PRESCRIPTION_NOT_FOUND = "Prescription not found";

    /**
     * Share the Medicines not available message
     */
    public static final String MEDICINE_NOT_AVAILABLE = "Medicine Not Available";

    /**
     * Share the Deleted message
     */
    public static final String DELETED_SUCCESSFULLY = " Has Deleted Successfully";

    /**
     * Share the Medicine not found message
     */
    public static final String MEDICINE_NOT_FOUND = "Medicine Not Found";

    /**
     * Share the product not found message
     */
    public static final String BRAND_ITEM_NOT_FOUND = "Product Not Found";

    /**
     * Share the brand not found
     */
    public static final String BRAND_NOT_FOUND = "Brand Not Found";

    /**
     * Share the email ID is already registered message
     */
    public static final String EMAIL_ID_EXISTS = "This EmailId is already registered";

    /**
     * Share the phone number is already registered message
     */
    public static final String PHONE_NUMBER_EXISTS = "This Phone number is already registered";

    /**
     * Share the phone number and email ID already registered message
     */
    public static final String EMAIL_ID_PHONE_NUMBER_EXISTS = "This Phone number and EmailId are already registered";

    /**
     * Share the no items present in the cart message
     */
    public static final String CART_ITEM_NOT_FOUND = "No Items In The Cart";

    /**
     * Share the warehouse not found message
     */
    public static final String WAREHOUSE_NOT_FOUND = "Warehouse not found";

    /**
     * Share that delete is not successful message
     */
    public static final String NOT_DELETED_SUCCESSFULLY = "Delete Aborted";

    /**
     * Share the products are not added to cart message
     */
    public static final String CAN_NOT_ADD_ITEMS_IN_CART = "Cannot Add Items In Cart.";

    /**
     * Share that there is no items in the cart for ordering message
     */
    public static final String ORDER_ITEM_NOT_FOUND = "There Is No Items to Order";

    /**
     * Regex pattern for medicine name
     */
    public static final String REGEX_FOR_MEDICINE_NAME = "^[\\w\\s]*$";

    /**
     * Regex pattern for description
     */
    public static final String REGEX_FOR_PARAGRAPHS = "^[\\w\\s-.,']*";

    /**
     * Regex pattern for text
     */
    public static final String REGEX_FOR_TEXT = "^[a-zA-z][a-zA-Z\\s]*$";

    /**
     * Regex pattern for date
     */
    public static final String REGEX_FOR_DATE = "^[0-9]{4}-[0-9]{2}-[0-9]{2}$";

    /**
     * Regex pattern for number
     */
    public static final String REGEX_FOR_NUMBER = "^[1-9][0-9]*$";
    /**
     * Regex pattern for plot number
     */
    public static final String REGEX_FOR_PLOT_NUMBER = "^[a-zA-Z0-9/]{1,10}$";

    /**
     * Regex pattern for pin code
     */
    public static final String REGEX_FOR_PIN_CODE = "^[1-9][0-9]{5}";

    /**
     * Regex pattern for Age
     */
    public static final String REGEX_FOR_AGE = "[0-9.]{1,3}";

    /**
     * Regex pattern for phone number
     */
    public static final String REGEX_FOR_PHONE_NUMBER = "^[6-9]{1}[0-9]{9}";

    /**
     * Regex Pattern for brand item name
     */
    public static final String REGEX_FOR_BRAND_ITEM_NAME = "^[a-zA-z][a-zA-Z0-9-\\s]*$";

    /**
     * Regex pattern Email ID
     */
    public static final String REGEX_FOR_EMAIL_ID = "^[a-z]{1}[a-z0-9._]+@[a-z0-9]+[.][a-z]*$";

    /**
     * Share the products are removed successful message
     */
    public static final String REMOVED_SUCCESSFULLY = "Items Removed Successfully";

    /**
     * Share the order is cancelled successful message
     */
    public static final String ORDER_CANCELED_SUCCESSFULLY = "Order Cancelled Successfully";

    /**
     * Share the order cannot be cancelled message
     */
    public static final String CAN_NOT_CANCEL_THE_ORDER = "Cannot Cancel The Order";

    /**
     * Share that there is no items in the order to cancel message
     */
    public static final String NO_ITEM_TO_CANCEL_THE_ORDER = "There Is No Item To Cancel The Order";

    /**
     * Share that there is no history of orders message
     */
    public static final String NO_HISTORY_OF_ORDERS = "There Is No History Of Orders";

    /**
     * Share that Invalid_credentials message
     */
    public static final String INVALID_CREDENTIALS = "Invalid_credentials";

    /**
     * Share the discount cannot be added message
     */
    public static final String CAN_NOT_ADD_DISCOUNT = "Cannot Add Discount";

    /**
     * Share that there is no discount available message
     */
    public static final String NO_DISCOUNT = "There Is No Discount";

    /**
     * Share that the discount cannot be deleted message
     */
    public static final String CAN_NOT_DELETE = "Cannot Deleted";

    /**
     * Share that the user details updated successfully message
     */
    public static final String UPDATED_SUCCESSFULLY = " Has Updated Successfully";

    /**
     * Share that can not order message.
     */
    public static final String CAN_NOT_ORDER = "Can Not Order.";

    /**
     * Share the address not found message.
     */
    public static final String ADDRESS_NOT_FOUND = "Address not found";

    /**
     * Share cart add successfully
     */
    public static final String ADDED_TO_CART = "Items added  to cart Successfully";

    /**
     * Share if the brand item name is already exist
     */
    public static final String BRAND_ITEM_NAME_EXIST = "Brand Item name already exist";

    /**
     * Share if the medicine name already exist
     */
    public static final String MEDICINE_NAME_EXIST = "Medicine name already exist";

    /**
     * Share if the brand name is already exist
     */
    public static final String BRAND_NAME_EXIST = "Brand name already exist";

    /**
     * Share if the warehouse name is already exist
     */
    public static final String WAREHOUSE_NAME_EXIST = "Warehouse name already exist";

    /**
     * Share if order is successful
     */
    public static final String ORDER_SUCCESSFUL = "Order successful";

    /**
     * Share if order is empty
     */
    public static final String ORDER_IS_EMPTY = "Order details is empty";


    /**
     * Share if order can not update
     */
    public static final String CAN_NOT_UPDATE_DISCOUNT = "Can not able to update discount";


    /**
     * Share if discount id is empty
     */
    public static final String ID_REQUIRED_TO_UPDATE_DISCOUNT = "Discount id is required to update discount.";
    
     /**   
     * Share that there is no medicine in the prescription message
     */
    public static final String NO_MEDICINE_IN_THE_PRESCRIPTION = "There is no medicines in the prescription";
}
