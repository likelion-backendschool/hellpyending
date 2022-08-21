package com.example.hellpyending.src.gym;

import com.example.hellpyending.src.gym.entity.GetAddressRes;
import com.example.hellpyending.src.gym.entity.GetAddressResInterface;
import com.example.hellpyending.src.gym.entity.Gym;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RequiredArgsConstructor
@RequestMapping("/gym")
@Controller
public class gymController {
    String GEOCODE_URL = "http://dapi.kakao.com/v2/local/search/address.json?query=";
    String GEOCODE_USER_INFO = "KakaoAK 2419daa123eb8bb5f1117be4431f18bc";

    private final gymRepository gymRepository;
    private final gymService gymService;

    @ResponseBody
    @GetMapping("/saveLoad")
    public List<String> desc_to_lag() {

        URL obj;

        try {
            List<Gym> gymList = this.gymRepository.findAll();
            List<String> result_list = new ArrayList<>();
            for (Gym g : gymList) {
                if (g.getLat() != 0 || g.getLng() != 0) {
                    continue;
                }

                String address = g.getGymAddress();
                if (address.equals("") || address.equals(null)) {
                    continue;
                }
                System.out.println(address);
                String test_List = URLEncoder.encode(address, "UTF-8");
                System.out.println("address" + test_List);
                obj = new URL(GEOCODE_URL + test_List);

                HttpURLConnection con = (HttpURLConnection) obj.openConnection();

                //get으로 받아오면 된다. 자세한 사항은 카카오개발자센터에 나와있다.
                con.setRequestMethod("GET");
                con.setRequestProperty("Authorization", GEOCODE_USER_INFO);
                con.setRequestProperty("content-type", "application/json");
                con.setDoOutput(true);
                con.setUseCaches(false);
                con.setDefaultUseCaches(false);

                Charset charset = Charset.forName("UTF-8");
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), charset));

                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }

                //response 객체를 출력해보자
                String json_response = response.toString();
                JSONObject jObject = new JSONObject(json_response);
                JSONArray jarray = jObject.getJSONArray("documents");
                JSONObject jObject2 = jarray.getJSONObject(0);
                double x = jObject2.getDouble("x");
                double y = jObject2.getDouble("y");
                g.setLng(x);
                g.setLat(y);

                this.gymRepository.save(g);
                System.out.println("x : " + x + ", Y :" + y);
                // 위도, 경도 따로 뺴서, db에 저장이 목표~
                result_list.add(response.toString());


            }

            return result_list;

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }


    @ResponseBody
    @GetMapping("/sync_address")
    public List<String> sync_address() {
        Random rand = new Random();
        ArrayList<Integer> price = new ArrayList<>();

        final int[] month_1= {30000,20000,50000,45000,65000,25000};

        URL obj;

        try {
            List<Gym> gymList = this.gymRepository.findAll();
            List<String> result_list = new ArrayList<>();
            for (Gym g : gymList) {
                if (g.getLat() == 0 || g.getLng() == 0) {
                    continue;
                }

                String address = g.getGymAddress();
                if (address.equals("") || address.equals(null)) {
                    continue;
                }
                System.out.println(address);
                String test_List = URLEncoder.encode(address, "UTF-8");
                System.out.println("address" + test_List);
                obj = new URL(GEOCODE_URL + test_List);

                HttpURLConnection con = (HttpURLConnection) obj.openConnection();

                //get으로 받아오면 된다. 자세한 사항은 카카오개발자센터에 나와있다.
                con.setRequestMethod("GET");
                con.setRequestProperty("Authorization", GEOCODE_USER_INFO);
                con.setRequestProperty("content-type", "application/json");
                con.setDoOutput(true);
                con.setUseCaches(false);
                con.setDefaultUseCaches(false);

                Charset charset = Charset.forName("UTF-8");
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), charset));

                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }

                //response 객체를 출력해보자
                String json_response = response.toString();
                JSONObject jObject = new JSONObject(json_response);
                JSONArray jarray = jObject.getJSONArray("documents");
                JSONObject jObject2 = jarray.getJSONObject(0);
                JSONObject jObject3 = jObject2.getJSONObject("address");

                String address_1 = jObject3.getString("region_1depth_name");
                String address_2 = jObject3.getString("region_2depth_name");
                String address_3 = jObject3.getString("region_3depth_h_name");

                g.setAddress_1st(address_1);
                g.setAddress_2st(address_2);
                g.setAddress_3st(address_3);


                int price_1 = month_1[rand.nextInt(5)];
                g.setPer_1_month(price_1);
                g.setPer_3_months(price_1*3);
                g.setPer_6_months((int) ((price_1*6)*0.9));
                g.setPer_12_months((int) ((price_1*12)*0.8));

                this.gymRepository.save(g);

                System.out.println(g.getAddress_1st()+g.getAddress_2st()+g.getAddress_3st());
                // 위도, 경도 따로 뺴서, db에 저장이 목표~


            }

            return result_list;

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }

    @ResponseBody
    @GetMapping("/getGymList")
    public GetAddressRes showList() {
        List<GetAddressResInterface> reslut = this.gymRepository.findyGymList();
        GetAddressRes getAddressRes = new GetAddressRes();
        getAddressRes.setPositions(reslut);
        return getAddressRes;

    }

    @RequestMapping("/search")
    public String kakao2() {
        return "gymList_from_kakaoMap";
    }

}


