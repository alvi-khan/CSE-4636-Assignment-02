package com.example.labassignment02;

import java.io.*;
import java.util.Arrays;
import java.util.stream.IntStream;

/**
 * JavaBean to hold client's cart information
 */
public class CartBean implements Serializable {
    private int totalItemTypes = 3;
    private int itemsInCart[] = new int[totalItemTypes];

    public CartBean() {

        for (int i = 0; i< totalItemTypes; i++)    itemsInCart[i] = 0;
    }

    public int getTotalItemTypes() {
        return totalItemTypes;
    }

    public int getTotalItemCount() { return IntStream.of(itemsInCart).sum(); }

    public int getItemCount(int itemNumber) {
        return itemsInCart[itemNumber];
    }

    public void incrementItemCount(int itemNumber) {
        itemsInCart[itemNumber]++;
    }

    public void decrementItemCount(int itemNumber) {
        itemsInCart[itemNumber]--;
    }

    public void resetCart() { Arrays.fill(itemsInCart, 0); }

    /**
     * Saves this CartBean object to an external file
     */
    public void saveToFile(String filepath) {
        try {
            File saveFile = new File(filepath);
            saveFile.createNewFile();   // create file if it does not exist
            FileOutputStream fileOutputStream = new FileOutputStream(saveFile);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(this);
            objectOutputStream.close();
            fileOutputStream.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieves CartBean object for the specified user
     */
    public static CartBean getCart(String filepath) {
        File saveFile = new File(filepath);
        if (saveFile.exists()) {
            // existing client; retrieve old cart
            try {
                FileInputStream fileInputStream = new FileInputStream(saveFile);
                ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
                CartBean cartBean = (CartBean) objectInputStream.readObject();
                objectInputStream.close();
                fileInputStream.close();
                return cartBean;
            } catch (Exception ex) {
                // error retrieving cart; return new cart
                ex.printStackTrace();
                return new CartBean();
            }
        }
        // new client; return new cart
        else    return new CartBean();
    }
}
