package pojo;

import java.util.List;

/**
 *
 * The pojo class for the response of /restaurants/{restaurant_id}.
 *
 * @author jingjiejiang
 * @history Sep 18, 2021
 *
 */
public class RestaurantResponse {

    private int status_code;
    private List<Data> data;

    public int getStatus_code() {
        return status_code;
    }

    public void setStatus_code(int status_code) {
        this.status_code = status_code;
    }

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }
}
