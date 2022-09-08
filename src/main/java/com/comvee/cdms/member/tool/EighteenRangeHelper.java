package com.comvee.cdms.member.tool;

import java.util.HashMap;
import java.util.Map;

/** 中国 3~17岁儿童每岁、身高对应的血压
 * Created by : wyc
 * Created time 2020/2/19 8:44
 */
public class EighteenRangeHelper {

    public static Map<String,Float> getEighteenRange(int age,double height,int hypBfz,int sex){
        Map<String, Float> resultMap = new HashMap<>();
        Float sbpLow = 0f;  //收缩压低值
        Float sbpHigh = 0f;  //收缩压高值
        Float dbpLow = 0f;  //舒张压低值
        Float dbpHigh = 0f;  //舒张压高值

        if (age >= 3 && age <= 17){
            if (age == 3){
                if (sex == 1){  //男性
                    if (height < 96){
                        sbpLow = 88f;
                        dbpLow = 54f;
                    }else if (height >= 96 && height <= 97){
                        sbpLow = 88f;
                        dbpLow = 54f;
                    }else if (height >= 98 && height <= 100){
                        sbpLow = 89f;
                        dbpLow = 54f;
                    }else if (height >= 101 && height <= 103){
                        sbpLow = 90f;
                        dbpLow = 54f;
                    }else if (height >= 104 && height <= 106){
                        sbpLow = 91f;
                        dbpLow = 55f;
                    }else if (height >= 107 && height <= 108){
                        sbpLow = 92f;
                        dbpLow = 55f;
                    }else if (height >= 109){
                        sbpLow = 93f;
                        dbpLow = 55f;
                    }
                    //50th≤ xx ＜95th
                }else{  //女性
                    if (height < 95){
                        sbpLow = 87f;
                        dbpLow = 55f;
                    }else if (height >= 95 && height <= 96){
                        sbpLow = 88f;
                        dbpLow = 55f;
                    }else if (height >= 97 && height <= 99){
                        sbpLow = 88f;
                        dbpLow = 55f;
                    }else if (height >= 100 && height <= 102){
                        sbpLow = 89f;
                        dbpLow = 55f;
                    }else if (height >= 103 && height <= 105){
                        sbpLow = 90f;
                        dbpLow = 55f;
                    }else if (height >= 106 && height <= 107){
                        sbpLow = 91f;
                        dbpLow = 55f;
                    }else if (height >= 108){
                        sbpLow = 91f;
                        dbpLow = 56f;
                    }

                }
            }else if (age == 4){
                if (sex == 1){  //男
                    if (height < 102){
                        sbpLow = 89f;
                        dbpLow = 55f;
                    }else if (height >= 102 && height <= 104){
                        sbpLow = 90f;
                        dbpLow = 55f;
                    }else if (height >= 105 && height <= 107){
                        sbpLow = 91f;
                        dbpLow = 55f;
                    }else if (height >= 108 && height <= 110){
                        sbpLow = 92f;
                        dbpLow = 56f;
                    }else if (height >= 111 && height <= 113){
                        sbpLow = 93f;
                        dbpLow = 56f;
                    }else if (height >= 114 && height <= 116){
                        sbpLow = 94f;
                        dbpLow = 56f;
                    }else if (height >= 117){
                        sbpLow = 95f;
                        dbpLow = 56f;
                    }
                }else{ //女
                    if (height < 101){
                        sbpLow = 89f;
                        dbpLow = 56f;
                    }else if (height >= 101 && height <= 103){
                        sbpLow = 89f;
                        dbpLow = 56f;
                    }else if (height >= 104 && height <= 106){
                        sbpLow = 90f;
                        dbpLow = 56f;
                    }else if (height >= 107 && height <= 109){
                        sbpLow = 91f;
                        dbpLow = 56f;
                    }else if (height >= 110 && height <= 112){
                        sbpLow = 92f;
                        dbpLow = 56f;
                    }else if (height >= 113 && height <= 114){
                        sbpLow = 93f;
                        dbpLow = 56f;
                    }else if (height >= 115){
                        sbpLow = 93f;
                        dbpLow = 56f;
                    }
                }
            }else if (age == 5){
                if (sex == 1){  //男
                    if (height < 109){
                        sbpLow = 92f;
                        dbpLow = 56f;
                    }else if (height >= 109 && height <= 110){
                        sbpLow = 92f;
                        dbpLow = 56f;
                    }else if (height >= 111 && height <= 113){
                        sbpLow = 93f;
                        dbpLow = 56f;
                    }else if (height >= 114 && height <= 117){
                        sbpLow = 94f;
                        dbpLow = 57f;
                    }else if (height >= 118 && height <= 120){
                        sbpLow = 95f;
                        dbpLow = 57f;
                    }else if (height >= 121 && height <= 123){
                        sbpLow = 96f;
                        dbpLow = 58f;
                    }else if (height >= 124){
                        sbpLow = 97f;
                        dbpLow = 58f;
                    }
                }else {  //女
                    if (height < 108){
                        sbpLow = 91f;
                        dbpLow = 56f;
                    }else if (height >= 108 && height <= 109){
                        sbpLow = 91f;
                        dbpLow = 56f;
                    }else if (height >= 110 && height <= 112){
                        sbpLow = 92f;
                        dbpLow = 56f;
                    }else if (height >= 113 && height <= 116){
                        sbpLow = 93f;
                        dbpLow = 57f;
                    }else if (height >= 117 && height <= 119){
                        sbpLow = 93f;
                        dbpLow = 57f;
                    }else if (height >= 120 && height <= 122){
                        sbpLow = 94f;
                        dbpLow = 58f;
                    }else if (height >= 123){
                        sbpLow = 95f;
                        dbpLow = 58f;
                    }
                }
            }else if (age == 6){
                if (sex == 1){  //男
                    if (height < 114){
                        sbpLow = 93f;
                        dbpLow = 57f;
                    }else if (height >= 114 && height <= 116){
                        sbpLow = 94f;
                        dbpLow = 57f;
                    }else if (height >= 117 && height <= 119){
                        sbpLow = 95f;
                        dbpLow = 58f;
                    }else if (height >= 120 && height <= 123){
                        sbpLow = 96f;
                        dbpLow = 58f;
                    }else if (height >= 124 && height <= 126){
                        sbpLow = 97f;
                        dbpLow = 59f;
                    }else if (height >= 127 && height <= 129){
                        sbpLow = 98f;
                        dbpLow = 59f;
                    }else if (height >= 130){
                        sbpLow = 99f;
                        dbpLow = 60f;
                    }
                }else {  //女
                    if (height < 113){
                        sbpLow = 92f;
                        dbpLow = 57f;
                    }else if (height >= 113 && height <= 114){
                        sbpLow = 92f;
                        dbpLow = 57f;
                    }else if (height >= 115 && height <= 118){
                        sbpLow = 93f;
                        dbpLow = 57f;
                    }else if (height >= 119 && height <= 121){
                        sbpLow = 94f;
                        dbpLow = 58f;
                    }else if (height >= 122 && height <= 125){
                        sbpLow = 95f;
                        dbpLow = 58f;
                    }else if (height >= 126 && height <= 128){
                        sbpLow = 96f;
                        dbpLow = 59f;
                    }else if (height >= 129){
                        sbpLow = 97f;
                        dbpLow = 59f;
                    }
                }
            }else if (age == 7){
                if (sex == 1){ //男
                    if (height < 118){
                        sbpLow = 94f;
                        dbpLow = 58f;
                    }else if (height >= 118 && height <= 120){
                        sbpLow = 95f;
                        dbpLow = 58f;
                    }else if (height >= 121 && height <= 123){
                        sbpLow = 96f;
                        dbpLow = 59f;
                    }else if (height >= 124 && height <= 127){
                        sbpLow = 97f;
                        dbpLow = 59f;
                    }else if (height >= 128 && height <= 131){
                        sbpLow = 98f;
                        dbpLow = 60f;
                    }else if (height >= 132 && height <= 135){
                        sbpLow = 100f;
                        dbpLow = 61f;
                    }else if (height >= 136){
                        sbpLow = 100f;
                        dbpLow = 62f;
                    }
                }else {  //女
                    if (height < 116){
                        sbpLow = 93f;
                        dbpLow = 57f;
                    }else if (height >= 116 && height <= 118){
                        sbpLow = 93f;
                        dbpLow = 57f;
                    }else if (height >= 119 && height <= 122){
                        sbpLow = 94f;
                        dbpLow = 58f;
                    }else if (height >= 123 && height <= 126){
                        sbpLow = 95f;
                        dbpLow = 59f;
                    }else if (height >= 127 && height <= 130){
                        sbpLow = 96f;
                        dbpLow = 59f;
                    }else if (height >= 131 && height <= 133){
                        sbpLow = 97f;
                        dbpLow = 60f;
                    }else if (height >= 134){
                        sbpLow = 98f;
                        dbpLow = 61f;
                    }
                }
            }else if (age == 8){
                if (sex == 1){ //男
                    if (height < 121){
                        sbpLow = 95f;
                        dbpLow = 59f;
                    }else if (height >= 121 && height <= 123){
                        sbpLow = 95f;
                        dbpLow = 59f;
                    }else if (height >= 124 && height <= 127){
                        sbpLow = 97f;
                        dbpLow = 60f;
                    }else if (height >= 128 && height <= 132){
                        sbpLow = 98f;
                        dbpLow = 61f;
                    }else if (height >= 133 && height <= 136){
                        sbpLow = 99f;
                        dbpLow = 62f;
                    }else if (height >= 137 && height <= 139){
                        sbpLow = 101f;
                        dbpLow = 62f;
                    }else if (height >= 140){
                        sbpLow = 102f;
                        dbpLow = 63f;
                    }
                }else {  //女
                    if (height < 120){
                        sbpLow = 94f;
                        dbpLow = 58f;
                    }else if (height >= 120 && height <= 122){
                        sbpLow = 94f;
                        dbpLow = 58f;
                    }else if (height >= 123 && height <= 126){
                        sbpLow = 95f;
                        dbpLow = 59f;
                    }else if (height >= 127 && height <= 131){
                        sbpLow = 96f;
                        dbpLow = 60f;
                    }else if (height >= 132 && height <= 135){
                        sbpLow = 98f;
                        dbpLow = 61f;
                    }else if (height >= 136 && height <= 138){
                        sbpLow = 99f;
                        dbpLow = 61f;
                    }else if (height >= 139){
                        sbpLow = 100f;
                        dbpLow = 62f;
                    }
                }
            }else if (age == 9){
                if (sex == 1){ //男
                    if (height < 125){
                        sbpLow = 96f;
                        dbpLow = 60f;
                    }else if (height >= 125 && height <= 128){
                        sbpLow = 96f;
                        dbpLow = 60f;
                    }else if (height >= 129 && height <= 132){
                        sbpLow = 98f;
                        dbpLow = 61f;
                    }else if (height >= 133 && height <= 137){
                        sbpLow = 99f;
                        dbpLow = 62f;
                    }else if (height >= 138 && height <= 142){
                        sbpLow = 101f;
                        dbpLow = 63f;
                    }else if (height >= 143 && height <= 145){
                        sbpLow = 102f;
                        dbpLow = 64f;
                    }else if (height >= 146){
                        sbpLow = 103f;
                        dbpLow = 64f;
                    }
                }else {  //女
                    if (height < 124){
                        sbpLow = 95f;
                        dbpLow = 59f;
                    }else if (height >= 124 && height <= 127){
                        sbpLow = 95f;
                        dbpLow = 59f;
                    }else if (height >= 128 && height <= 132){
                        sbpLow = 97f;
                        dbpLow = 60f;
                    }else if (height >= 133 && height <= 136){
                        sbpLow = 98f;
                        dbpLow = 61f;
                    }else if (height >= 137 && height <= 141){
                        sbpLow = 100f;
                        dbpLow = 62f;
                    }else if (height >= 142 && height <= 145){
                        sbpLow = 101f;
                        dbpLow = 63f;
                    }else if (height >= 146){
                        sbpLow = 102f;
                        dbpLow = 63f;
                    }
                }
            }else if (age == 10){
                if (sex == 1){ //男
                    if (height < 130){
                        sbpLow = 97f;
                        dbpLow = 61f;
                    }else if (height >= 130 && height <= 132){
                        sbpLow = 98f;
                        dbpLow = 62f;
                    }else if (height >= 133 && height <= 137){
                        sbpLow = 99f;
                        dbpLow = 62f;
                    }else if (height >= 138 && height <= 142){
                        sbpLow = 101f;
                        dbpLow = 63f;
                    }else if (height >= 143 && height <= 147){
                        sbpLow = 102f;
                        dbpLow = 64f;
                    }else if (height >= 148 && height <= 151){
                        sbpLow = 104f;
                        dbpLow = 64f;
                    }else if (height >= 152){
                        sbpLow = 105f;
                        dbpLow = 64f;
                    }
                }else {  //女
                    if (height < 130){
                        sbpLow = 96f;
                        dbpLow = 60f;
                    }else if (height >= 130 && height <= 133){
                        sbpLow = 97f;
                        dbpLow = 61f;
                    }else if (height >= 134 && height <= 138){
                        sbpLow = 99f;
                        dbpLow = 62f;
                    }else if (height >= 139 && height <= 143){
                        sbpLow = 100f;
                        dbpLow = 63f;
                    }else if (height >= 144 && height <= 147){
                        sbpLow = 101f;
                        dbpLow = 63f;
                    }else if (height >= 148 && height <= 151){
                        sbpLow = 103f;
                        dbpLow = 63f;
                    }else if (height >= 152){
                        sbpLow = 103f;
                        dbpLow = 64f;
                    }
                }
            }else if (age == 11){
                if (sex == 1){ //男
                    if (height < 134){
                        sbpLow = 98f;
                        dbpLow = 62f;
                    }else if (height >= 134 && height <= 137){
                        sbpLow = 99f;
                        dbpLow = 63f;
                    }else if (height >= 138 && height <= 142){
                        sbpLow = 100f;
                        dbpLow = 64f;
                    }else if (height >= 143 && height <= 148){
                        sbpLow = 102f;
                        dbpLow = 64f;
                    }else if (height >= 149 && height <= 153){
                        sbpLow = 104f;
                        dbpLow = 64f;
                    }else if (height >= 154 && height <= 157){
                        sbpLow = 106f;
                        dbpLow = 64f;
                    }else if (height >= 158){
                        sbpLow = 106f;
                        dbpLow = 64f;
                    }
                }else {  //女
                    if (height < 136){
                        sbpLow = 98f;
                        dbpLow = 62f;
                    }else if (height >= 136 && height <= 139){
                        sbpLow = 99f;
                        dbpLow = 62f;
                    }else if (height >= 140 && height <= 144){
                        sbpLow = 101f;
                        dbpLow = 63f;
                    }else if (height >= 145 && height <= 149){
                        sbpLow = 102f;
                        dbpLow = 64f;
                    }else if (height >= 150 && height <= 154){
                        sbpLow = 103f;
                        dbpLow = 64f;
                    }else if (height >= 155 && height <= 157){
                        sbpLow = 104f;
                        dbpLow = 64f;
                    }else if (height >= 158){
                        sbpLow = 104f;
                        dbpLow = 64f;
                    }
                }
            }else if (age == 12){
                if (sex == 1){ //男
                    if (height < 140){
                        sbpLow = 100f;
                        dbpLow = 64f;
                    }else if (height >= 140 && height <= 144){
                        sbpLow = 101f;
                        dbpLow = 64f;
                    }else if (height >= 145 && height <= 149){
                        sbpLow = 102f;
                        dbpLow = 65f;
                    }else if (height >= 150 && height <= 155){
                        sbpLow = 104f;
                        dbpLow = 65f;
                    }else if (height >= 156 && height <= 160){
                        sbpLow = 106f;
                        dbpLow = 65f;
                    }else if (height >= 161 && height <= 164){
                        sbpLow = 108f;
                        dbpLow = 65f;
                    }else if (height >= 165){
                        sbpLow = 108f;
                        dbpLow = 65f;
                    }
                }else {  //女
                    if (height < 142){
                        sbpLow = 100f;
                        dbpLow = 63f;
                    }else if (height >= 142 && height <= 145){
                        sbpLow = 101f;
                        dbpLow = 63f;
                    }else if (height >= 146 && height <= 150){
                        sbpLow = 102f;
                        dbpLow = 64f;
                    }else if (height >= 151 && height <= 154){
                        sbpLow = 103f;
                        dbpLow = 64f;
                    }else if (height >= 155 && height <= 158){
                        sbpLow = 104f;
                        dbpLow = 64f;
                    }else if (height >= 159 && height <= 162){
                        sbpLow = 105f;
                        dbpLow = 64f;
                    }else if (height >= 163){
                        sbpLow = 105f;
                        dbpLow = 64f;
                    }
                }
            }else if (age == 13){
                if (sex == 1){ //男
                    if (height < 147){
                        sbpLow = 102f;
                        dbpLow = 65f;
                    }else if (height >= 147 && height <= 151){
                        sbpLow = 103f;
                        dbpLow = 65f;
                    }else if (height >= 152 && height <= 156){
                        sbpLow = 104f;
                        dbpLow = 65f;
                    }else if (height >= 157 && height <= 162){
                        sbpLow = 106f;
                        dbpLow = 65f;
                    }else if (height >= 163 && height <= 167){
                        sbpLow = 108f;
                        dbpLow = 65f;
                    }else if (height >= 168 && height <= 171){
                        sbpLow = 110f;
                        dbpLow = 66f;
                    }else if (height >= 172){
                        sbpLow = 110f;
                        dbpLow = 66f;
                    }
                }else {  //女
                    if (height < 147){
                        sbpLow = 101f;
                        dbpLow = 64f;
                    }else if (height >= 147 && height <= 149){
                        sbpLow = 102f;
                        dbpLow = 64f;
                    }else if (height >= 150 && height <= 153){
                        sbpLow = 103f;
                        dbpLow = 64f;
                    }else if (height >= 154 && height <= 157){
                        sbpLow = 104f;
                        dbpLow = 65f;
                    }else if (height >= 158 && height <= 161){
                        sbpLow = 105f;
                        dbpLow = 65f;
                    }else if (height >= 162 && height <= 164){
                        sbpLow = 105f;
                        dbpLow = 65f;
                    }else if (height >= 165){
                        sbpLow = 105f;
                        dbpLow = 65f;
                    }
                }
            }else if (age == 14){
                if (sex == 1){ //男
                    if (height < 154){
                        sbpLow = 103f;
                        dbpLow = 65f;
                    }else if (height >= 154 && height <= 157){
                        sbpLow = 104f;
                        dbpLow = 65f;
                    }else if (height >= 158 && height <= 162){
                        sbpLow = 106f;
                        dbpLow = 65f;
                    }else if (height >= 163 && height <= 167){
                        sbpLow = 108f;
                        dbpLow = 65f;
                    }else if (height >= 168 && height <= 172){
                        sbpLow = 109f;
                        dbpLow = 66f;
                    }else if (height >= 173 && height <= 176){
                        sbpLow = 111f;
                        dbpLow = 66f;
                    }else if (height >= 177){
                        sbpLow = 112f;
                        dbpLow = 67f;
                    }
                }else {  //女
                    if (height < 149){
                        sbpLow = 102f;
                        dbpLow = 65f;
                    }else if (height >= 149 && height <= 152){
                        sbpLow = 103f;
                        dbpLow = 65f;
                    }else if (height >= 153 && height <= 155){
                        sbpLow = 104f;
                        dbpLow = 65f;
                    }else if (height >= 156 && height <= 159){
                        sbpLow = 104f;
                        dbpLow = 65f;
                    }else if (height >= 160 && height <= 163){
                        sbpLow = 105f;
                        dbpLow = 65f;
                    }else if (height >= 164 && height <= 166){
                        sbpLow = 105f;
                        dbpLow = 65f;
                    }else if (height >= 167){
                        sbpLow = 106f;
                        dbpLow = 65f;
                    }
                }
            }else if (age == 15){
                if (sex == 1){ //男
                    if (height < 158){
                        sbpLow = 105f;
                        dbpLow = 65f;
                    }else if (height >= 158 && height <= 161){
                        sbpLow = 106f;
                        dbpLow = 65f;
                    }else if (height >= 162 && height <= 166){
                        sbpLow = 107f;
                        dbpLow = 66f;
                    }else if (height >= 167 && height <= 170){
                        sbpLow = 109f;
                        dbpLow = 66f;
                    }else if (height >= 171 && height <= 174){
                        sbpLow = 110f;
                        dbpLow = 66f;
                    }else if (height >= 175 && height <= 178){
                        sbpLow = 112f;
                        dbpLow = 67f;
                    }else if (height >= 179){
                        sbpLow = 113f;
                        dbpLow = 67f;
                    }
                }else {  //女
                    if (height < 151){
                        sbpLow = 103f;
                        dbpLow = 65f;
                    }else if (height >= 151 && height <= 152){
                        sbpLow = 103f;
                        dbpLow = 65f;
                    }else if (height >= 153 && height <= 156){
                        sbpLow = 104f;
                        dbpLow = 65f;
                    }else if (height >= 157 && height <= 160){
                        sbpLow = 105f;
                        dbpLow = 65f;
                    }else if (height >= 161 && height <= 163){
                        sbpLow = 105f;
                        dbpLow = 65f;
                    }else if (height >= 164 && height <= 166){
                        sbpLow = 105f;
                        dbpLow = 65f;
                    }else if (height >= 167){
                        sbpLow = 106f;
                        dbpLow = 65f;
                    }
                }
            }else if (age == 16){
                if (sex == 1){ //男
                    if (height < 161){
                        sbpLow = 105f;
                        dbpLow = 66f;
                    }else if (height >= 161 && height <= 164){
                        sbpLow = 106f;
                        dbpLow = 66f;
                    }else if (height >= 165 && height <= 168){
                        sbpLow = 107f;
                        dbpLow = 66f;
                    }else if (height >= 169 && height <= 172){
                        sbpLow = 109f;
                        dbpLow = 66f;
                    }else if (height >= 173 && height <= 176){
                        sbpLow = 111f;
                        dbpLow = 67f;
                    }else if (height >= 177 && height <= 179){
                        sbpLow = 112f;
                        dbpLow = 67f;
                    }else if (height >= 180){
                        sbpLow = 113f;
                        dbpLow = 67f;
                    }
                }else {  //女
                    if (height < 151){
                        sbpLow = 103f;
                        dbpLow = 65f;
                    }else if (height >= 151 && height <= 153){
                        sbpLow = 103f;
                        dbpLow = 65f;
                    }else if (height >= 154 && height <= 157){
                        sbpLow = 104f;
                        dbpLow = 65f;
                    }else if (height >= 158 && height <= 160){
                        sbpLow = 105f;
                        dbpLow = 65f;
                    }else if (height >= 161 && height <= 164){
                        sbpLow = 105f;
                        dbpLow = 66f;
                    }else if (height >= 165 && height <= 167){
                        sbpLow = 106f;
                        dbpLow = 66f;
                    }else if (height >= 168){
                        sbpLow = 106f;
                        dbpLow = 66f;
                    }
                }
            }else if (age == 17){
                if (sex == 1){ //男
                    if (height < 163){
                        sbpLow = 106f;
                        dbpLow = 66f;
                    }else if (height >= 163 && height <= 165){
                        sbpLow = 107f;
                        dbpLow = 66f;
                    }else if (height >= 166 && height <= 169){
                        sbpLow = 108f;
                        dbpLow = 66f;
                    }else if (height >= 170 && height <= 173){
                        sbpLow = 109f;
                        dbpLow = 67f;
                    }else if (height >= 174 && height <= 177){
                        sbpLow = 111f;
                        dbpLow = 67f;
                    }else if (height >= 178 && height <= 180){
                        sbpLow = 112f;
                        dbpLow = 67f;
                    }else if (height >= 181){
                        sbpLow = 113f;
                        dbpLow = 68f;
                    }
                }else {  //女
                    if (height < 152){
                        sbpLow = 103f;
                        dbpLow = 66f;
                    }else if (height >= 152 && height <= 154){
                        sbpLow = 104f;
                        dbpLow = 66f;
                    }else if (height >= 155 && height <= 157){
                        sbpLow = 104f;
                        dbpLow = 66f;
                    }else if (height >= 158 && height <= 161){
                        sbpLow = 105f;
                        dbpLow = 66f;
                    }else if (height >= 162 && height <= 164){
                        sbpLow = 105f;
                        dbpLow = 66f;
                    }else if (height >= 165 && height <= 167){
                        sbpLow = 106f;
                        dbpLow = 66f;
                    }else if (height >=168){
                        sbpLow = 106f;
                        dbpLow = 66f;
                    }
                }
            }

            if (hypBfz == 1){
                if (age == 3){
                    if (sex == 1){  //男性
                        if (height < 96){
                            sbpHigh = 99f;
                            dbpHigh = 62f;
                        }else if (height >= 96 && height <= 97){
                            sbpHigh = 100f;
                            dbpHigh = 63f;
                        }else if (height >= 98 && height <= 100){
                            sbpHigh = 101f;
                            dbpHigh = 63f;
                        }else if (height >= 101 && height <= 103){
                            sbpHigh = 102f;
                            dbpHigh = 63f;
                        }else if (height >= 104 && height <= 106){
                            sbpHigh = 103f;
                            dbpHigh = 63f;
                        }else if (height >= 107 && height <= 108){
                            sbpHigh = 104f;
                            dbpHigh = 63f;
                        }else if (height >= 109){
                            sbpHigh = 105f;
                            dbpHigh = 63f;
                        }
                        //50th≤ xx ＜95th
                    }else{  //女性
                        if (height < 95){
                            sbpHigh = 99f;
                            dbpHigh = 63f;
                        }else if (height >= 95 && height <= 96){
                            sbpHigh = 99f;
                            dbpHigh = 63f;
                        }else if (height >= 97 && height <= 99){
                            sbpHigh = 100f;
                            dbpHigh = 64f;
                        }else if (height >= 100 && height <= 102){
                            sbpHigh = 101f;
                            dbpHigh = 64f;
                        }else if (height >= 103 && height <= 105){
                            sbpHigh = 102f;
                            dbpHigh = 64f;
                        }else if (height >= 106 && height <= 107){
                            sbpHigh = 103f;
                            dbpHigh = 64f;
                        }else if (height >= 108){
                            sbpHigh = 103f;
                            dbpHigh = 64f;
                        }

                    }
                }else if (age == 4){
                    if (sex == 1){  //男
                        if (height < 102){
                            sbpHigh = 101f;
                            dbpHigh = 64f;
                        }else if (height >= 102 && height <= 104){
                            sbpHigh = 102f;
                            dbpHigh = 64f;
                        }else if (height >= 105 && height <= 107){
                            sbpHigh = 103f;
                            dbpHigh = 64f;
                        }else if (height >= 108 && height <= 110){
                            sbpHigh = 104f;
                            dbpHigh = 64f;
                        }else if (height >= 111 && height <= 113){
                            sbpHigh = 106f;
                            dbpHigh = 64f;
                        }else if (height >= 114 && height <= 116){
                            sbpHigh = 107f;
                            dbpHigh = 65f;
                        }else if (height >= 117){
                            sbpHigh = 107f;
                            dbpHigh = 65f;
                        }
                    }else{ //女
                        if (height < 101){
                            sbpHigh = 101f;
                            dbpHigh = 64f;
                        }else if (height >= 101 && height <= 103){
                            sbpHigh = 101f;
                            dbpHigh = 64f;
                        }else if (height >= 104 && height <= 106){
                            sbpHigh = 102f;
                            dbpHigh = 64f;
                        }else if (height >= 107 && height <= 109){
                            sbpHigh = 103f;
                            dbpHigh = 64f;
                        }else if (height >= 110 && height <= 112){
                            sbpHigh = 104f;
                            dbpHigh = 65f;
                        }else if (height >= 113 && height <= 114){
                            sbpHigh = 105f;
                            dbpHigh = 65f;
                        }else if (height >= 115){
                            sbpHigh = 105f;
                            dbpHigh = 65f;
                        }
                    }
                }else if (age == 5){
                    if (sex == 1){  //男
                        if (height < 109){
                            sbpHigh = 104f;
                            dbpHigh = 65f;
                        }else if (height >= 109 && height <= 110){
                            sbpHigh = 104f;
                            dbpHigh = 65f;
                        }else if (height >= 111 && height <= 113){
                            sbpHigh = 105f;
                            dbpHigh = 65f;
                        }else if (height >= 114 && height <= 117){
                            sbpHigh = 106f;
                            dbpHigh = 66f;
                        }else if (height >= 118 && height <= 120){
                            sbpHigh = 108f;
                            dbpHigh = 66f;
                        }else if (height >= 121 && height <= 123){
                            sbpHigh = 109f;
                            dbpHigh = 67f;
                        }else if (height >= 124){
                            sbpHigh = 110f;
                            dbpHigh = 67f;
                        }
                    }else {  //女
                        if (height < 108){
                            sbpHigh = 103f;
                            dbpHigh = 65f;
                        }else if (height >= 108 && height <= 109){
                            sbpHigh = 103f;
                            dbpHigh = 65f;
                        }else if (height >= 110 && height <= 112){
                            sbpHigh = 104f;
                            dbpHigh = 65f;
                        }else if (height >= 113 && height <= 116){
                            sbpHigh = 105f;
                            dbpHigh = 65f;
                        }else if (height >= 117 && height <= 119){
                            sbpHigh = 106f;
                            dbpHigh = 66f;
                        }else if (height >= 120 && height <= 122){
                            sbpHigh = 107f;
                            dbpHigh = 66f;
                        }else if (height >= 123){
                            sbpHigh = 108f;
                            dbpHigh = 67f;
                        }
                    }
                }else if (age == 6){
                    if (sex == 1){  //男
                        if (height < 114){
                            sbpHigh = 105f;
                            dbpHigh = 66f;
                        }else if (height >= 114 && height <= 116){
                            sbpHigh = 106f;
                            dbpHigh = 66f;
                        }else if (height >= 117 && height <= 119){
                            sbpHigh = 107f;
                            dbpHigh = 66f;
                        }else if (height >= 120 && height <= 123){
                            sbpHigh = 108f;
                            dbpHigh = 67f;
                        }else if (height >= 124 && height <= 126){
                            sbpHigh = 110f;
                            dbpHigh = 68f;
                        }else if (height >= 127 && height <= 129){
                            sbpHigh = 111f;
                            dbpHigh = 69f;
                        }else if (height >= 130){
                            sbpHigh = 112f;
                            dbpHigh = 69f;
                        }
                    }else {  //女
                        if (height < 113){
                            sbpHigh = 104f;
                            dbpHigh = 65f;
                        }else if (height >= 113 && height <= 114){
                            sbpHigh = 105f;
                            dbpHigh = 66f;
                        }else if (height >= 115 && height <= 118){
                            sbpHigh = 106f;
                            dbpHigh = 66f;
                        }else if (height >= 119 && height <= 121){
                            sbpHigh = 107f;
                            dbpHigh = 67f;
                        }else if (height >= 122 && height <= 125){
                            sbpHigh = 108f;
                            dbpHigh = 67f;
                        }else if (height >= 126 && height <= 128){
                            sbpHigh = 109f;
                            dbpHigh = 68f;
                        }else if (height >= 129){
                            sbpHigh = 110f;
                            dbpHigh = 69f;
                        }
                    }
                }else if (age == 7){
                    if (sex == 1){ //男
                        if (height < 118){
                            sbpHigh = 106f;
                            dbpHigh = 67f;
                        }else if (height >= 118 && height <= 120){
                            sbpHigh = 107f;
                            dbpHigh = 67f;
                        }else if (height >= 121 && height <= 123){
                            sbpHigh = 108f;
                            dbpHigh = 68f;
                        }else if (height >= 124 && height <= 127){
                            sbpHigh = 110f;
                            dbpHigh = 68f;
                        }else if (height >= 128 && height <= 131){
                            sbpHigh = 112f;
                            dbpHigh = 70f;
                        }else if (height >= 132 && height <= 135){
                            sbpHigh = 113f;
                            dbpHigh = 71f;
                        }else if (height >= 136){
                            sbpHigh = 114f;
                            dbpHigh = 71f;
                        }
                    }else {  //女
                        if (height < 116){
                            sbpHigh = 105f;
                            dbpHigh = 66f;
                        }else if (height >= 116 && height <= 118){
                            sbpHigh = 106f;
                            dbpHigh = 66f;
                        }else if (height >= 119 && height <= 122){
                            sbpHigh = 107f;
                            dbpHigh = 67f;
                        }else if (height >= 123 && height <= 126){
                            sbpHigh = 108f;
                            dbpHigh = 68f;
                        }else if (height >= 127 && height <= 130){
                            sbpHigh = 109f;
                            dbpHigh = 69f;
                        }else if (height >= 131 && height <= 133){
                            sbpHigh = 111f;
                            dbpHigh = 69f;
                        }else if (height >= 134){
                            sbpHigh = 112f;
                            dbpHigh = 70f;
                        }
                    }
                }else if (age == 8){
                    if (sex == 1){ //男
                        if (height < 121){
                            sbpHigh = 108f;
                            dbpHigh = 68f;
                        }else if (height >= 121 && height <= 123){
                            sbpHigh = 108f;
                            dbpHigh = 68f;
                        }else if (height >= 124 && height <= 127){
                            sbpHigh = 110f;
                            dbpHigh = 69f;
                        }else if (height >= 128 && height <= 132){
                            sbpHigh = 111f;
                            dbpHigh = 70f;
                        }else if (height >= 133 && height <= 136){
                            sbpHigh = 113f;
                            dbpHigh = 71f;
                        }else if (height >= 137 && height <= 139){
                            sbpHigh = 114f;
                            dbpHigh = 72f;
                        }else if (height >= 140){
                            sbpHigh = 115f;
                            dbpHigh = 73f;
                        }
                    }else {  //女
                        if (height < 120){
                            sbpHigh = 106f;
                            dbpHigh = 67f;
                        }else if (height >= 120 && height <= 122){
                            sbpHigh = 107f;
                            dbpHigh = 67f;
                        }else if (height >= 123 && height <= 126){
                            sbpHigh = 108f;
                            dbpHigh = 68f;
                        }else if (height >= 127 && height <= 131){
                            sbpHigh = 109f;
                            dbpHigh = 69f;
                        }else if (height >= 132 && height <= 135){
                            sbpHigh = 111f;
                            dbpHigh = 70f;
                        }else if (height >= 136 && height <= 138){
                            sbpHigh = 112f;
                            dbpHigh = 71f;
                        }else if (height >= 139){
                            sbpHigh = 113f;
                            dbpHigh = 71f;
                        }
                    }
                }else if (age == 9){
                    if (sex == 1){ //男
                        if (height < 125){
                            sbpHigh = 109f;
                            dbpHigh = 69f;
                        }else if (height >= 125 && height <= 128){
                            sbpHigh = 109f;
                            dbpHigh = 69f;
                        }else if (height >= 129 && height <= 132){
                            sbpHigh = 111f;
                            dbpHigh = 71f;
                        }else if (height >= 133 && height <= 137){
                            sbpHigh = 113f;
                            dbpHigh = 72f;
                        }else if (height >= 138 && height <= 142){
                            sbpHigh = 115f;
                            dbpHigh = 73f;
                        }else if (height >= 143 && height <= 145){
                            sbpHigh = 116f;
                            dbpHigh = 73f;
                        }else if (height >= 146){
                            sbpHigh = 117f;
                            dbpHigh = 74f;
                        }
                    }else {  //女
                        if (height < 124){
                            sbpHigh = 108f;
                            dbpHigh = 68f;
                        }else if (height >= 124 && height <= 127){
                            sbpHigh = 108f;
                            dbpHigh = 68f;
                        }else if (height >= 128 && height <= 132){
                            sbpHigh = 110f;
                            dbpHigh = 69f;
                        }else if (height >= 133 && height <= 136){
                            sbpHigh = 111f;
                            dbpHigh = 71f;
                        }else if (height >= 137 && height <= 141){
                            sbpHigh = 113f;
                            dbpHigh = 72f;
                        }else if (height >= 142 && height <= 145){
                            sbpHigh = 114f;
                            dbpHigh = 72f;
                        }else if (height >= 146){
                            sbpHigh = 115f;
                            dbpHigh = 73f;
                        }
                    }
                }else if (age == 10){
                    if (sex == 1){ //男
                        if (height < 130){
                            sbpHigh = 110f;
                            dbpHigh = 70f;
                        }else if (height >= 130 && height <= 132){
                            sbpHigh = 111f;
                            dbpHigh = 71f;
                        }else if (height >= 133 && height <= 137){
                            sbpHigh = 113f;
                            dbpHigh = 72f;
                        }else if (height >= 138 && height <= 142){
                            sbpHigh = 115f;
                            dbpHigh = 73f;
                        }else if (height >= 143 && height <= 147){
                            sbpHigh = 117f;
                            dbpHigh = 74f;
                        }else if (height >= 148 && height <= 151){
                            sbpHigh = 118f;
                            dbpHigh = 74f;
                        }else if (height >= 152){
                            sbpHigh = 119f;
                            dbpHigh = 74f;
                        }
                    }else {  //女
                        if (height < 130){
                            sbpHigh = 109f;
                            dbpHigh = 69f;
                        }else if (height >= 130 && height <= 133){
                            sbpHigh = 110f;
                            dbpHigh = 70f;
                        }else if (height >= 134 && height <= 138){
                            sbpHigh = 112f;
                            dbpHigh = 71f;
                        }else if (height >= 139 && height <= 143){
                            sbpHigh = 113f;
                            dbpHigh = 72f;
                        }else if (height >= 144 && height <= 147){
                            sbpHigh = 115f;
                            dbpHigh = 73f;
                        }else if (height >= 148 && height <= 151){
                            sbpHigh = 116f;
                            dbpHigh = 73f;
                        }else if (height >= 152){
                            sbpHigh = 117f;
                            dbpHigh = 73f;
                        }
                    }
                }else if (age == 11){
                    if (sex == 1){ //男
                        if (height < 134){
                            sbpHigh = 111f;
                            dbpHigh = 72f;
                        }else if (height >= 134 && height <= 137){
                            sbpHigh = 112f;
                            dbpHigh = 72f;
                        }else if (height >= 138 && height <= 142){
                            sbpHigh = 114f;
                            dbpHigh = 73f;
                        }else if (height >= 143 && height <= 148){
                            sbpHigh = 116f;
                            dbpHigh = 74f;
                        }else if (height >= 149 && height <= 153){
                            sbpHigh = 119f;
                            dbpHigh = 74f;
                        }else if (height >= 154 && height <= 157){
                            sbpHigh = 120f;
                            dbpHigh = 74f;
                        }else if (height >= 158){
                            sbpHigh = 121f;
                            dbpHigh = 74f;
                        }
                    }else {  //女
                        if (height < 136){
                            sbpHigh = 112f;
                            dbpHigh = 71f;
                        }else if (height >= 136 && height <= 139){
                            sbpHigh = 113f;
                            dbpHigh = 72f;
                        }else if (height >= 140 && height <= 144){
                            sbpHigh = 114f;
                            dbpHigh = 73f;
                        }else if (height >= 145 && height <= 149){
                            sbpHigh = 116f;
                            dbpHigh = 73f;
                        }else if (height >= 150 && height <= 154){
                            sbpHigh = 117f;
                            dbpHigh = 74f;
                        }else if (height >= 155 && height <= 157){
                            sbpHigh = 118f;
                            dbpHigh = 74f;
                        }else if (height >= 158){
                            sbpHigh = 118f;
                            dbpHigh = 74f;
                        }
                    }
                }else if (age == 12){
                    if (sex == 1){ //男
                        if (height < 140){
                            sbpHigh = 113f;
                            dbpHigh = 73f;
                        }else if (height >= 140 && height <= 144){
                            sbpHigh = 115f;
                            dbpHigh = 74f;
                        }else if (height >= 145 && height <= 149){
                            sbpHigh = 117f;
                            dbpHigh = 75f;
                        }else if (height >= 150 && height <= 155){
                            sbpHigh = 119f;
                            dbpHigh = 75f;
                        }else if (height >= 156 && height <= 160){
                            sbpHigh = 121f;
                            dbpHigh = 75f;
                        }else if (height >= 161 && height <= 164){
                            sbpHigh = 123f;
                            dbpHigh = 75f;
                        }else if (height >= 165){
                            sbpHigh = 124f;
                            dbpHigh = 75f;
                        }
                    }else {  //女
                        if (height < 142){
                            sbpHigh = 113f;
                            dbpHigh = 73f;
                        }else if (height >= 142 && height <= 145){
                            sbpHigh = 114f;
                            dbpHigh = 73f;
                        }else if (height >= 146 && height <= 150){
                            sbpHigh = 116f;
                            dbpHigh = 74f;
                        }else if (height >= 151 && height <= 154){
                            sbpHigh = 117f;
                            dbpHigh = 74f;
                        }else if (height >= 155 && height <= 158){
                            sbpHigh = 118f;
                            dbpHigh = 74f;
                        }else if (height >= 159 && height <= 162){
                            sbpHigh = 119f;
                            dbpHigh = 74f;
                        }else if (height >= 163){
                            sbpHigh = 119f;
                            dbpHigh = 74f;
                        }
                    }
                }else if (age == 13){
                    if (sex == 1){ //男
                        if (height < 147){
                            sbpHigh = 116f;
                            dbpHigh = 75f;
                        }else if (height >= 147 && height <= 151){
                            sbpHigh = 117f;
                            dbpHigh = 75f;
                        }else if (height >= 152 && height <= 156){
                            sbpHigh = 119f;
                            dbpHigh = 75f;
                        }else if (height >= 157 && height <= 162){
                            sbpHigh = 121f;
                            dbpHigh = 75f;
                        }else if (height >= 163 && height <= 167){
                            sbpHigh = 123f;
                            dbpHigh = 75f;
                        }else if (height >= 168 && height <= 171){
                            sbpHigh = 125f;
                            dbpHigh = 76f;
                        }else if (height >= 172){
                            sbpHigh = 126f;
                            dbpHigh = 76f;
                        }
                    }else {  //女
                        if (height < 147){
                            sbpHigh = 115f;
                            dbpHigh = 74f;
                        }else if (height >= 147 && height <= 149){
                            sbpHigh = 116f;
                            dbpHigh = 74f;
                        }else if (height >= 150 && height <= 153){
                            sbpHigh = 117f;
                            dbpHigh = 74f;
                        }else if (height >= 154 && height <= 157){
                            sbpHigh = 118f;
                            dbpHigh = 74f;
                        }else if (height >= 158 && height <= 161){
                            sbpHigh = 119f;
                            dbpHigh = 74f;
                        }else if (height >= 162 && height <= 164){
                            sbpHigh = 119f;
                            dbpHigh = 74f;
                        }else if (height >= 165){
                            sbpHigh = 119f;
                            dbpHigh = 75f;
                        }
                    }
                }else if (age == 14){
                    if (sex == 1){ //男
                        if (height < 154){
                            sbpHigh = 118f;
                            dbpHigh = 75f;
                        }else if (height >= 154 && height <= 157){
                            sbpHigh = 119f;
                            dbpHigh = 75f;
                        }else if (height >= 158 && height <= 162){
                            sbpHigh = 121f;
                            dbpHigh = 75f;
                        }else if (height >= 163 && height <= 167){
                            sbpHigh = 123f;
                            dbpHigh = 75f;
                        }else if (height >= 168 && height <= 172){
                            sbpHigh = 125f;
                            dbpHigh = 76f;
                        }else if (height >= 173 && height <= 176){
                            sbpHigh = 127f;
                            dbpHigh = 76f;
                        }else if (height >= 177){
                            sbpHigh = 128f;
                            dbpHigh = 77f;
                        }
                    }else {  //女
                        if (height < 149){
                            sbpHigh = 116f;
                            dbpHigh = 74f;
                        }else if (height >= 149 && height <= 152){
                            sbpHigh = 117f;
                            dbpHigh = 75f;
                        }else if (height >= 153 && height <= 155){
                            sbpHigh = 118f;
                            dbpHigh = 75f;
                        }else if (height >= 156 && height <= 159){
                            sbpHigh = 118f;
                            dbpHigh = 75f;
                        }else if (height >= 160 && height <= 163){
                            sbpHigh = 119f;
                            dbpHigh = 75f;
                        }else if (height >= 164 && height <= 166){
                            sbpHigh = 119f;
                            dbpHigh = 75f;
                        }else if (height >= 167){
                            sbpHigh = 120f;
                            dbpHigh = 75f;
                        }
                    }
                }else if (age == 15){
                    if (sex == 1){ //男
                        if (height < 158){
                            sbpHigh = 120f;
                            dbpHigh = 76f;
                        }else if (height >= 158 && height <= 161){
                            sbpHigh = 121f;
                            dbpHigh = 76f;
                        }else if (height >= 162 && height <= 166){
                            sbpHigh = 122f;
                            dbpHigh = 76f;
                        }else if (height >= 167 && height <= 170){
                            sbpHigh = 124f;
                            dbpHigh = 76f;
                        }else if (height >= 171 && height <= 174){
                            sbpHigh = 126f;
                            dbpHigh = 77f;
                        }else if (height >= 175 && height <= 178){
                            sbpHigh = 128f;
                            dbpHigh = 77f;
                        }else if (height >= 179){
                            sbpHigh = 129f;
                            dbpHigh = 77f;
                        }
                    }else {  //女
                        if (height < 151){
                            sbpHigh = 116f;
                            dbpHigh = 75f;
                        }else if (height >= 151 && height <= 152){
                            sbpHigh = 117f;
                            dbpHigh = 75f;
                        }else if (height >= 153 && height <= 156){
                            sbpHigh = 118f;
                            dbpHigh = 75f;
                        }else if (height >= 157 && height <= 160){
                            sbpHigh = 119f;
                            dbpHigh = 75f;
                        }else if (height >= 161 && height <= 163){
                            sbpHigh = 119f;
                            dbpHigh = 75f;
                        }else if (height >= 164 && height <= 166){
                            sbpHigh = 120f;
                            dbpHigh = 75f;
                        }else if (height >= 167){
                            sbpHigh = 120f;
                            dbpHigh = 75f;
                        }
                    }
                }else if (age == 16){
                    if (sex == 1){ //男
                        if (height < 161){
                            sbpHigh = 121f;
                            dbpHigh = 76f;
                        }else if (height >= 161 && height <= 164){
                            sbpHigh = 121f;
                            dbpHigh = 76f;
                        }else if (height >= 165 && height <= 168){
                            sbpHigh = 123f;
                            dbpHigh = 76f;
                        }else if (height >= 169 && height <= 172){
                            sbpHigh = 125f;
                            dbpHigh = 76f;
                        }else if (height >= 173 && height <= 176){
                            sbpHigh = 126f;
                            dbpHigh = 77f;
                        }else if (height >= 177 && height <= 179){
                            sbpHigh = 128f;
                            dbpHigh = 77f;
                        }else if (height >= 180){
                            sbpHigh = 129f;
                            dbpHigh = 78f;
                        }
                    }else {  //女
                        if (height < 151){
                            sbpHigh = 117f;
                            dbpHigh = 75f;
                        }else if (height >= 151 && height <= 153){
                            sbpHigh = 117f;
                            dbpHigh = 75f;
                        }else if (height >= 154 && height <= 157){
                            sbpHigh = 118f;
                            dbpHigh = 75f;
                        }else if (height >= 158 && height <= 160){
                            sbpHigh = 119f;
                            dbpHigh = 75f;
                        }else if (height >= 161 && height <= 164){
                            sbpHigh = 119f;
                            dbpHigh = 76f;
                        }else if (height >= 165 && height <= 167){
                            sbpHigh = 120f;
                            dbpHigh = 76f;
                        }else if (height >= 168){
                            sbpHigh = 120f;
                            dbpHigh = 76f;
                        }
                    }
                }else if (age == 17){
                    if (sex == 1){ //男
                        if (height < 163){
                            sbpHigh = 121f;
                            dbpHigh = 76f;
                        }else if (height >= 163 && height <= 165){
                            sbpHigh = 122f;
                            dbpHigh = 76f;
                        }else if (height >= 166 && height <= 169){
                            sbpHigh = 124f;
                            dbpHigh = 76f;
                        }else if (height >= 170 && height <= 173){
                            sbpHigh = 125f;
                            dbpHigh = 77f;
                        }else if (height >= 174 && height <= 177){
                            sbpHigh = 127f;
                            dbpHigh = 77f;
                        }else if (height >= 178 && height <= 180){
                            sbpHigh = 129f;
                            dbpHigh = 78f;
                        }else if (height >= 181){
                            sbpHigh = 129f;
                            dbpHigh = 78f;
                        }
                    }else {  //女
                        if (height < 152){
                            sbpHigh = 117f;
                            dbpHigh = 76f;
                        }else if (height >= 152 && height <= 154){
                            sbpHigh = 118f;
                            dbpHigh = 76f;
                        }else if (height >= 155 && height <= 157){
                            sbpHigh = 118f;
                            dbpHigh = 76f;
                        }else if (height >= 158 && height <= 161){
                            sbpHigh = 119f;
                            dbpHigh = 76f;
                        }else if (height >= 162 && height <= 164){
                            sbpHigh = 119f;
                            dbpHigh = 76f;
                        }else if (height >= 165 && height <= 167){
                            sbpHigh = 120f;
                            dbpHigh = 76f;
                        }else if (height >=168){
                            sbpHigh = 120f;
                            dbpHigh = 76f;
                        }
                    }
                }
            }else {
                if (age == 3){
                    if (sex == 1){  //男性
                        if (height < 96){
                            sbpHigh = 102f;
                            dbpHigh = 65f;
                        }else if (height >= 96 && height <= 97){
                            sbpHigh = 103f;
                            dbpHigh = 65f;
                        }else if (height >= 98 && height <= 100){
                            sbpHigh = 104f;
                            dbpHigh = 66f;
                        }else if (height >= 101 && height <= 103){
                            sbpHigh = 105f;
                            dbpHigh = 66f;
                        }else if (height >= 104 && height <= 106){
                            sbpHigh = 107f;
                            dbpHigh = 66f;
                        }else if (height >= 107 && height <= 108){
                            sbpHigh = 107f;
                            dbpHigh = 66f;
                        }else if (height >= 109){
                            sbpHigh = 108f;
                            dbpHigh = 66f;
                        }
                        //50th≤ xx ＜95th
                    }else{  //女性
                        if (height < 95){
                            sbpHigh = 102f;
                            dbpHigh = 67f;
                        }else if (height >= 95 && height <= 96){
                            sbpHigh = 103f;
                            dbpHigh = 67f;
                        }else if (height >= 97 && height <= 99){
                            sbpHigh = 103f;
                            dbpHigh = 67f;
                        }else if (height >= 100 && height <= 102){
                            sbpHigh = 104f;
                            dbpHigh = 67f;
                        }else if (height >= 103 && height <= 105){
                            sbpHigh = 105f;
                            dbpHigh = 67f;
                        }else if (height >= 106 && height <= 107){
                            sbpHigh = 106f;
                            dbpHigh = 67f;
                        }else if (height >= 108){
                            sbpHigh = 107f;
                            dbpHigh = 67f;
                        }

                    }
                }else if (age == 4){
                    if (sex == 1){  //男
                        if (height < 102){
                            sbpHigh = 104f;
                            dbpHigh = 67f;
                        }else if (height >= 102 && height <= 104){
                            sbpHigh = 105f;
                            dbpHigh = 67f;
                        }else if (height >= 105 && height <= 107){
                            sbpHigh = 106f;
                            dbpHigh = 67f;
                        }else if (height >= 108 && height <= 110){
                            sbpHigh = 108f;
                            dbpHigh = 67f;
                        }else if (height >= 111 && height <= 113){
                            sbpHigh = 109f;
                            dbpHigh = 67f;
                        }else if (height >= 114 && height <= 116){
                            sbpHigh = 110f;
                            dbpHigh = 68f;
                        }else if (height >= 117){
                            sbpHigh = 111f;
                            dbpHigh = 68f;
                        }
                    }else{ //女
                        if (height < 101){
                            sbpHigh = 105f;
                            dbpHigh = 67f;
                        }else if (height >= 101 && height <= 103){
                            sbpHigh = 105f;
                            dbpHigh = 67f;
                        }else if (height >= 104 && height <= 106){
                            sbpHigh = 106f;
                            dbpHigh = 67f;
                        }else if (height >= 107 && height <= 109){
                            sbpHigh = 107f;
                            dbpHigh = 67f;
                        }else if (height >= 110 && height <= 112){
                            sbpHigh = 107f;
                            dbpHigh = 68f;
                        }else if (height >= 113 && height <= 114){
                            sbpHigh = 109f;
                            dbpHigh = 68f;
                        }else if (height >= 115){
                            sbpHigh = 109f;
                            dbpHigh = 68f;
                        }
                    }
                }else if (age == 5){
                    if (sex == 1){  //男
                        if (height < 109){
                            sbpHigh = 107f;
                            dbpHigh = 68f;
                        }else if (height >= 109 && height <= 110){
                            sbpHigh = 107f;
                            dbpHigh = 68f;
                        }else if (height >= 111 && height <= 113){
                            sbpHigh = 109f;
                            dbpHigh = 68f;
                        }else if (height >= 114 && height <= 117){
                            sbpHigh = 110f;
                            dbpHigh = 69f;
                        }else if (height >= 118 && height <= 120){
                            sbpHigh = 111f;
                            dbpHigh = 69f;
                        }else if (height >= 121 && height <= 123){
                            sbpHigh = 112f;
                            dbpHigh = 70f;
                        }else if (height >= 124){
                            sbpHigh = 113f;
                            dbpHigh = 70f;
                        }
                    }else {  //女
                        if (height < 108){
                            sbpHigh = 106f;
                            dbpHigh = 68f;
                        }else if (height >= 108 && height <= 109){
                            sbpHigh = 107f;
                            dbpHigh = 68f;
                        }else if (height >= 110 && height <= 112){
                            sbpHigh = 107f;
                            dbpHigh = 68f;
                        }else if (height >= 113 && height <= 116){
                            sbpHigh = 109f;
                            dbpHigh = 68f;
                        }else if (height >= 117 && height <= 119){
                            sbpHigh = 109f;
                            dbpHigh = 69f;
                        }else if (height >= 120 && height <= 122){
                            sbpHigh = 111f;
                            dbpHigh = 70f;
                        }else if (height >= 123){
                            sbpHigh = 111f;
                            dbpHigh = 70f;
                        }
                    }
                }else if (age == 6){
                    if (sex == 1){  //男
                        if (height < 114){
                            sbpHigh = 109f;
                            dbpHigh = 69f;
                        }else if (height >= 114 && height <= 116){
                            sbpHigh = 120f;
                            dbpHigh = 69f;
                        }else if (height >= 117 && height <= 119){
                            sbpHigh = 111f;
                            dbpHigh = 69f;
                        }else if (height >= 120 && height <= 123){
                            sbpHigh = 112f;
                            dbpHigh = 70f;
                        }else if (height >= 124 && height <= 126){
                            sbpHigh = 113f;
                            dbpHigh = 71f;
                        }else if (height >= 127 && height <= 129){
                            sbpHigh = 115f;
                            dbpHigh = 72f;
                        }else if (height >= 130){
                            sbpHigh = 116f;
                            dbpHigh = 73f;
                        }
                    }else {  //女
                        if (height < 113){
                            sbpHigh = 108f;
                            dbpHigh = 69f;
                        }else if (height >= 113 && height <= 114){
                            sbpHigh = 108f;
                            dbpHigh = 69f;
                        }else if (height >= 115 && height <= 118){
                            sbpHigh = 109f;
                            dbpHigh = 69f;
                        }else if (height >= 119 && height <= 121){
                            sbpHigh = 110f;
                            dbpHigh = 70f;
                        }else if (height >= 122 && height <= 125){
                            sbpHigh = 112f;
                            dbpHigh = 71f;
                        }else if (height >= 126 && height <= 128){
                            sbpHigh = 113f;
                            dbpHigh = 71f;
                        }else if (height >= 129){
                            sbpHigh = 114f;
                            dbpHigh = 72f;
                        }
                    }
                }else if (age == 7){
                    if (sex == 1){ //男
                        if (height < 118){
                            sbpHigh = 110f;
                            dbpHigh = 70f;
                        }else if (height >= 118 && height <= 120){
                            sbpHigh = 111f;
                            dbpHigh = 70f;
                        }else if (height >= 121 && height <= 123){
                            sbpHigh = 112f;
                            dbpHigh = 71f;
                        }else if (height >= 124 && height <= 127){
                            sbpHigh = 113f;
                            dbpHigh = 71f;
                        }else if (height >= 128 && height <= 131){
                            sbpHigh = 115f;
                            dbpHigh = 73f;
                        }else if (height >= 132 && height <= 135){
                            sbpHigh = 117f;
                            dbpHigh = 74f;
                        }else if (height >= 136){
                            sbpHigh = 117f;
                            dbpHigh = 74f;
                        }
                    }else {  //女
                        if (height < 116){
                            sbpHigh = 109f;
                            dbpHigh = 69f;
                        }else if (height >= 116 && height <= 118){
                            sbpHigh = 109f;
                            dbpHigh = 69f;
                        }else if (height >= 119 && height <= 122){
                            sbpHigh = 110f;
                            dbpHigh = 70f;
                        }else if (height >= 123 && height <= 126){
                            sbpHigh = 112f;
                            dbpHigh = 71f;
                        }else if (height >= 127 && height <= 130){
                            sbpHigh = 113f;
                            dbpHigh = 72f;
                        }else if (height >= 131 && height <= 133){
                            sbpHigh = 114f;
                            dbpHigh = 73f;
                        }else if (height >= 134){
                            sbpHigh = 115f;
                            dbpHigh = 73f;
                        }
                    }
                }else if (age == 8){
                    if (sex == 1){ //男
                        if (height < 121){
                            sbpHigh = 111f;
                            dbpHigh = 71f;
                        }else if (height >= 121 && height <= 123){
                            sbpHigh = 112f;
                            dbpHigh = 71f;
                        }else if (height >= 124 && height <= 127){
                            sbpHigh = 113f;
                            dbpHigh = 72f;
                        }else if (height >= 128 && height <= 132){
                            sbpHigh = 115f;
                            dbpHigh = 73f;
                        }else if (height >= 133 && height <= 136){
                            sbpHigh = 117f;
                            dbpHigh = 74f;
                        }else if (height >= 137 && height <= 139){
                            sbpHigh = 118f;
                            dbpHigh = 75f;
                        }else if (height >= 140){
                            sbpHigh = 119f;
                            dbpHigh = 76f;
                        }
                    }else {  //女
                        if (height < 120){
                            sbpHigh = 110f;
                            dbpHigh = 70f;
                        }else if (height >= 120 && height <= 122){
                            sbpHigh = 111f;
                            dbpHigh = 71f;
                        }else if (height >= 123 && height <= 126){
                            sbpHigh = 112f;
                            dbpHigh = 71f;
                        }else if (height >= 127 && height <= 131){
                            sbpHigh = 113f;
                            dbpHigh = 72f;
                        }else if (height >= 132 && height <= 135){
                            sbpHigh = 115f;
                            dbpHigh = 73f;
                        }else if (height >= 136 && height <= 138){
                            sbpHigh = 116f;
                            dbpHigh = 74f;
                        }else if (height >= 139){
                            sbpHigh = 117f;
                            dbpHigh = 75f;
                        }
                    }
                }else if (age == 9){
                    if (sex == 1){ //男
                        if (height < 125){
                            sbpHigh = 112f;
                            dbpHigh = 72f;
                        }else if (height >= 125 && height <= 128){
                            sbpHigh = 113f;
                            dbpHigh = 73f;
                        }else if (height >= 129 && height <= 132){
                            sbpHigh = 115f;
                            dbpHigh = 74f;
                        }else if (height >= 133 && height <= 137){
                            sbpHigh = 117f;
                            dbpHigh = 75f;
                        }else if (height >= 138 && height <= 142){
                            sbpHigh = 119f;
                            dbpHigh = 76f;
                        }else if (height >= 143 && height <= 145){
                            sbpHigh = 120f;
                            dbpHigh = 77f;
                        }else if (height >= 146){
                            sbpHigh = 121f;
                            dbpHigh = 77f;
                        }
                    }else {  //女
                        if (height < 124){
                            sbpHigh = 111f;
                            dbpHigh = 71f;
                        }else if (height >= 124 && height <= 127){
                            sbpHigh = 112f;
                            dbpHigh = 72f;
                        }else if (height >= 128 && height <= 132){
                            sbpHigh = 113f;
                            dbpHigh = 73f;
                        }else if (height >= 133 && height <= 136){
                            sbpHigh = 115f;
                            dbpHigh = 74f;
                        }else if (height >= 137 && height <= 141){
                            sbpHigh = 117f;
                            dbpHigh = 75f;
                        }else if (height >= 142 && height <= 145){
                            sbpHigh = 118f;
                            dbpHigh = 76f;
                        }else if (height >= 146){
                            sbpHigh = 119f;
                            dbpHigh = 76f;
                        }
                    }
                }else if (age == 10){
                    if (sex == 1){ //男
                        if (height < 130){
                            sbpHigh = 114f;
                            dbpHigh = 74f;
                        }else if (height >= 130 && height <= 132){
                            sbpHigh = 115f;
                            dbpHigh = 74f;
                        }else if (height >= 133 && height <= 137){
                            sbpHigh = 116f;
                            dbpHigh = 75f;
                        }else if (height >= 138 && height <= 142){
                            sbpHigh = 119f;
                            dbpHigh = 77f;
                        }else if (height >= 143 && height <= 147){
                            sbpHigh = 120f;
                            dbpHigh = 77f;
                        }else if (height >= 148 && height <= 151){
                            sbpHigh = 122f;
                            dbpHigh = 77f;
                        }else if (height >= 152){
                            sbpHigh = 123f;
                            dbpHigh = 77f;
                        }
                    }else {  //女
                        if (height < 130){
                            sbpHigh = 113f;
                            dbpHigh = 73f;
                        }else if (height >= 130 && height <= 133){
                            sbpHigh = 114f;
                            dbpHigh = 73f;
                        }else if (height >= 134 && height <= 138){
                            sbpHigh = 116f;
                            dbpHigh = 73f;
                        }else if (height >= 139 && height <= 143){
                            sbpHigh = 117f;
                            dbpHigh = 76f;
                        }else if (height >= 144 && height <= 147){
                            sbpHigh = 119f;
                            dbpHigh = 76f;
                        }else if (height >= 148 && height <= 151){
                            sbpHigh = 120f;
                            dbpHigh = 77f;
                        }else if (height >= 152){
                            sbpHigh = 121f;
                            dbpHigh = 77f;
                        }
                    }
                }else if (age == 11){
                    if (sex == 1){ //男
                        if (height < 134){
                            sbpHigh = 115f;
                            dbpHigh = 75f;
                        }else if (height >= 134 && height <= 137){
                            sbpHigh = 116f;
                            dbpHigh = 76f;
                        }else if (height >= 138 && height <= 142){
                            sbpHigh = 118f;
                            dbpHigh = 77f;
                        }else if (height >= 143 && height <= 148){
                            sbpHigh = 120f;
                            dbpHigh = 78f;
                        }else if (height >= 149 && height <= 153){
                            sbpHigh = 123f;
                            dbpHigh = 78f;
                        }else if (height >= 154 && height <= 157){
                            sbpHigh = 124f;
                            dbpHigh = 78f;
                        }else if (height >= 158){
                            sbpHigh = 125f;
                            dbpHigh = 78f;
                        }
                    }else {  //女
                        if (height < 136){
                            sbpHigh = 115f;
                            dbpHigh = 75f;
                        }else if (height >= 136 && height <= 139){
                            sbpHigh = 116f;
                            dbpHigh = 75f;
                        }else if (height >= 140 && height <= 144){
                            sbpHigh = 118f;
                            dbpHigh = 76f;
                        }else if (height >= 145 && height <= 149){
                            sbpHigh = 120f;
                            dbpHigh = 77f;
                        }else if (height >= 150 && height <= 154){
                            sbpHigh = 121f;
                            dbpHigh = 77f;
                        }else if (height >= 155 && height <= 157){
                            sbpHigh = 122f;
                            dbpHigh = 77f;
                        }else if (height >= 158){
                            sbpHigh = 122f;
                            dbpHigh = 77f;
                        }
                    }
                }else if (age == 12){
                    if (sex == 1){ //男
                        if (height < 140){
                            sbpHigh = 117f;
                            dbpHigh = 77f;
                        }else if (height >= 140 && height <= 144){
                            sbpHigh = 119f;
                            dbpHigh = 78f;
                        }else if (height >= 145 && height <= 149){
                            sbpHigh = 121f;
                            dbpHigh = 78f;
                        }else if (height >= 150 && height <= 155){
                            sbpHigh = 123f;
                            dbpHigh = 78f;
                        }else if (height >= 156 && height <= 160){
                            sbpHigh = 125f;
                            dbpHigh = 78f;
                        }else if (height >= 161 && height <= 164){
                            sbpHigh = 127f;
                            dbpHigh = 78f;
                        }else if (height >= 165){
                            sbpHigh = 128f;
                            dbpHigh = 78f;
                        }
                    }else {  //女
                        if (height < 142){
                            sbpHigh = 117f;
                            dbpHigh = 76f;
                        }else if (height >= 142 && height <= 145){
                            sbpHigh = 118f;
                            dbpHigh = 77f;
                        }else if (height >= 146 && height <= 150){
                            sbpHigh = 120f;
                            dbpHigh = 77f;
                        }else if (height >= 151 && height <= 154){
                            sbpHigh = 121f;
                            dbpHigh = 78f;
                        }else if (height >= 155 && height <= 158){
                            sbpHigh = 122f;
                            dbpHigh = 78f;
                        }else if (height >= 159 && height <= 162){
                            sbpHigh = 123f;
                            dbpHigh = 78f;
                        }else if (height >= 163){
                            sbpHigh = 123f;
                            dbpHigh = 78f;
                        }
                    }
                }else if (age == 13){
                    if (sex == 1){ //男
                        if (height < 147){
                            sbpHigh = 120f;
                            dbpHigh = 78f;
                        }else if (height >= 147 && height <= 151){
                            sbpHigh = 121f;
                            dbpHigh = 78f;
                        }else if (height >= 152 && height <= 156){
                            sbpHigh = 123f;
                            dbpHigh = 79f;
                        }else if (height >= 157 && height <= 162){
                            sbpHigh = 125f;
                            dbpHigh = 79f;
                        }else if (height >= 163 && height <= 167){
                            sbpHigh = 128f;
                            dbpHigh = 79f;
                        }else if (height >= 168 && height <= 171){
                            sbpHigh = 130f;
                            dbpHigh = 79f;
                        }else if (height >= 172){
                            sbpHigh = 130f;
                            dbpHigh = 79f;
                        }
                    }else {  //女
                        if (height < 147){
                            sbpHigh = 119f;
                            dbpHigh = 77f;
                        }else if (height >= 147 && height <= 149){
                            sbpHigh = 120f;
                            dbpHigh = 78f;
                        }else if (height >= 150 && height <= 153){
                            sbpHigh = 121f;
                            dbpHigh = 78f;
                        }else if (height >= 154 && height <= 157){
                            sbpHigh = 122f;
                            dbpHigh = 78f;
                        }else if (height >= 158 && height <= 161){
                            sbpHigh = 123f;
                            dbpHigh = 78f;
                        }else if (height >= 162 && height <= 164){
                            sbpHigh = 123f;
                            dbpHigh = 78f;
                        }else if (height >= 165){
                            sbpHigh = 123f;
                            dbpHigh = 78f;
                        }
                    }
                }else if (age == 14){
                    if (sex == 1){ //男
                        if (height < 154){
                            sbpHigh = 122f;
                            dbpHigh = 79f;
                        }else if (height >= 154 && height <= 157){
                            sbpHigh = 124f;
                            dbpHigh = 79f;
                        }else if (height >= 158 && height <= 162){
                            sbpHigh = 125f;
                            dbpHigh = 79f;
                        }else if (height >= 163 && height <= 167){
                            sbpHigh = 128f;
                            dbpHigh = 79f;
                        }else if (height >= 168 && height <= 172){
                            sbpHigh = 130f;
                            dbpHigh = 79f;
                        }else if (height >= 173 && height <= 176){
                            sbpHigh = 131f;
                            dbpHigh = 80f;
                        }else if (height >= 177){
                            sbpHigh = 133f;
                            dbpHigh = 80f;
                        }
                    }else {  //女
                        if (height < 149){
                            sbpHigh = 120f;
                            dbpHigh = 78f;
                        }else if (height >= 149 && height <= 152){
                            sbpHigh = 121f;
                            dbpHigh = 78f;
                        }else if (height >= 153 && height <= 155){
                            sbpHigh = 122f;
                            dbpHigh = 78f;
                        }else if (height >= 156 && height <= 159){
                            sbpHigh = 122f;
                            dbpHigh = 78f;
                        }else if (height >= 160 && height <= 163){
                            sbpHigh = 123f;
                            dbpHigh = 78f;
                        }else if (height >= 164 && height <= 166){
                            sbpHigh = 123f;
                            dbpHigh = 79f;
                        }else if (height >= 167){
                            sbpHigh = 124f;
                            dbpHigh = 79f;
                        }
                    }
                }else if (age == 15){
                    if (sex == 1){ //男
                        if (height < 158){
                            sbpHigh = 124f;
                            dbpHigh = 79f;
                        }else if (height >= 158 && height <= 161){
                            sbpHigh = 125f;
                            dbpHigh = 79f;
                        }else if (height >= 162 && height <= 166){
                            sbpHigh = 127f;
                            dbpHigh = 79f;
                        }else if (height >= 167 && height <= 170){
                            sbpHigh = 128f;
                            dbpHigh = 80f;
                        }else if (height >= 171 && height <= 174){
                            sbpHigh = 131f;
                            dbpHigh = 80f;
                        }else if (height >= 175 && height <= 178){
                            sbpHigh = 132f;
                            dbpHigh = 81f;
                        }else if (height >= 179){
                            sbpHigh = 133f;
                            dbpHigh = 81f;
                        }
                    }else {  //女
                        if (height < 151){
                            sbpHigh = 120f;
                            dbpHigh = 79f;
                        }else if (height >= 151 && height <= 152){
                            sbpHigh = 121f;
                            dbpHigh = 79f;
                        }else if (height >= 153 && height <= 156){
                            sbpHigh = 122f;
                            dbpHigh = 79f;
                        }else if (height >= 157 && height <= 160){
                            sbpHigh = 123f;
                            dbpHigh = 79f;
                        }else if (height >= 161 && height <= 163){
                            sbpHigh = 123f;
                            dbpHigh = 79f;
                        }else if (height >= 164 && height <= 166){
                            sbpHigh = 124f;
                            dbpHigh = 79f;
                        }else if (height >= 167){
                            sbpHigh = 124f;
                            dbpHigh = 79f;
                        }
                    }
                }else if (age == 16){
                    if (sex == 1){ //男
                        if (height < 161){
                            sbpHigh = 125f;
                            dbpHigh = 79f;
                        }else if (height >= 161 && height <= 164){
                            sbpHigh = 126f;
                            dbpHigh = 79f;
                        }else if (height >= 165 && height <= 168){
                            sbpHigh = 127f;
                            dbpHigh = 80f;
                        }else if (height >= 169 && height <= 172){
                            sbpHigh = 129f;
                            dbpHigh = 80f;
                        }else if (height >= 173 && height <= 176){
                            sbpHigh = 131f;
                            dbpHigh = 80f;
                        }else if (height >= 177 && height <= 179){
                            sbpHigh = 133f;
                            dbpHigh = 81f;
                        }else if (height >= 180){
                            sbpHigh = 134f;
                            dbpHigh = 81f;
                        }
                    }else {  //女
                        if (height < 151){
                            sbpHigh = 121f;
                            dbpHigh = 79f;
                        }else if (height >= 151 && height <= 153){
                            sbpHigh = 121f;
                            dbpHigh = 79f;
                        }else if (height >= 154 && height <= 157){
                            sbpHigh = 122f;
                            dbpHigh = 79f;
                        }else if (height >= 158 && height <= 160){
                            sbpHigh = 123f;
                            dbpHigh = 79f;
                        }else if (height >= 161 && height <= 164){
                            sbpHigh = 123f;
                            dbpHigh = 79f;
                        }else if (height >= 165 && height <= 167){
                            sbpHigh = 124f;
                            dbpHigh = 79f;
                        }else if (height >= 168){
                            sbpHigh = 124f;
                            dbpHigh = 79f;
                        }
                    }
                }else if (age == 17){
                    if (sex == 1){ //男
                        if (height < 163){
                            sbpHigh = 126f;
                            dbpHigh = 80f;
                        }else if (height >= 163 && height <= 165){
                            sbpHigh = 126f;
                            dbpHigh = 80f;
                        }else if (height >= 166 && height <= 169){
                            sbpHigh = 128f;
                            dbpHigh = 80f;
                        }else if (height >= 170 && height <= 173){
                            sbpHigh = 130f;
                            dbpHigh = 80f;
                        }else if (height >= 174 && height <= 177){
                            sbpHigh = 131f;
                            dbpHigh = 81f;
                        }else if (height >= 178 && height <= 180){
                            sbpHigh = 133f;
                            dbpHigh = 81f;
                        }else if (height >= 181){
                            sbpHigh = 134f;
                            dbpHigh = 82f;
                        }
                    }else {  //女
                        if (height < 152){
                            sbpHigh = 121f;
                            dbpHigh = 79f;
                        }else if (height >= 152 && height <= 154){
                            sbpHigh = 122f;
                            dbpHigh = 79f;
                        }else if (height >= 155 && height <= 157){
                            sbpHigh = 122f;
                            dbpHigh = 80f;
                        }else if (height >= 158 && height <= 161){
                            sbpHigh = 123f;
                            dbpHigh = 80f;
                        }else if (height >= 162 && height <= 164){
                            sbpHigh = 124f;
                            dbpHigh = 80f;
                        }else if (height >= 165 && height <= 167){
                            sbpHigh = 124f;
                            dbpHigh = 80f;
                        }else if (height >=168){
                            sbpHigh = 124f;
                            dbpHigh = 80f;
                        }
                    }
                }
            }
        }else if (age == 1){
            sbpHigh = 82f;
            dbpHigh = 55f;
        }else if (age == 2){
            sbpHigh = 84f;
            dbpHigh = 56f;
        }
        if (sbpHigh > 0){
            sbpHigh = sbpHigh - 1f;
        }
        if (dbpHigh > 0){
            dbpHigh = dbpHigh - 1f;
        }

        resultMap.put("sbpLow",sbpLow);
        resultMap.put("sbpHigh",sbpHigh);
        resultMap.put("dbpLow",dbpLow);
        resultMap.put("dbpHigh",dbpHigh);
        return resultMap;
    }


    public static Map<String,Double> getEighteenLevelRange(int age,double height,int sex){
        Map<String, Double> resultMap = new HashMap<>();
        Double sbpLow = 0d;  //收缩压低值
        Double sbpHigh = 0d;  //收缩压高值
        Double dbpLow = 0d;  //舒张压低值
        Double dbpHigh = 0d;  //舒张压高值

        if (age == 3){
            if (sex == 1){  //男性
                if (height < 96){
                    sbpLow = 88d;
                    dbpLow = 54d;
                    sbpHigh = 108d;
                    dbpHigh = 72d;
                }else if (height >= 96 && height <= 97){
                    sbpLow = 88d;
                    dbpLow = 54d;
                    sbpHigh = 109d;
                    dbpHigh = 72d;
                }else if (height >= 98 && height <= 100){
                    sbpLow = 89d;
                    dbpLow = 54d;
                    sbpHigh = 110d;
                    dbpHigh = 72d;
                }else if (height >= 101 && height <= 103){
                    sbpLow = 90d;
                    dbpLow = 54d;
                    sbpHigh = 112d;
                    dbpHigh = 73d;
                }else if (height >= 104 && height <= 106){
                    sbpLow = 91d;
                    dbpLow = 55d;
                    sbpHigh = 113d;
                    dbpHigh = 73d;
                }else if (height >= 107 && height <= 108){
                    sbpLow = 92d;
                    dbpLow = 55d;
                    sbpHigh = 114d;
                    dbpHigh = 73d;
                }else if (height >= 109){
                    sbpLow = 93d;
                    dbpLow = 55d;
                    sbpHigh = 115d;
                    dbpHigh = 73d;
                }
                //50th≤ xx ＜95th
            }else{  //女性
                if (height < 95){
                    sbpLow = 87d;
                    dbpLow = 55d;
                    sbpHigh = 108d;
                    dbpHigh = 74d;
                }else if (height >= 95 && height <= 96){
                    sbpLow = 88d;
                    dbpLow = 55d;
                    sbpHigh = 109d;
                    dbpHigh = 74d;
                }else if (height >= 97 && height <= 99){
                    sbpLow = 88d;
                    dbpLow = 55d;
                    sbpHigh = 110d;
                    dbpHigh = 74d;
                }else if (height >= 100 && height <= 102){
                    sbpLow = 89d;
                    dbpLow = 55d;
                    sbpHigh = 111d;
                    dbpHigh = 74d;
                }else if (height >= 103 && height <= 105){
                    sbpLow = 90d;
                    dbpLow = 55d;
                    sbpHigh = 112d;
                    dbpHigh = 74d;
                }else if (height >= 106 && height <= 107){
                    sbpLow = 91d;
                    dbpLow = 55d;
                    sbpHigh = 113d;
                    dbpHigh = 75d;
                }else if (height >= 108){
                    sbpLow = 91d;
                    dbpLow = 56d;
                    sbpHigh = 113d;
                    dbpHigh = 75d;
                }

            }
        }else if (age == 4){
            if (sex == 1){  //男
                if (height < 102){
                    sbpLow = 89d;
                    dbpLow = 55d;
                    sbpHigh = 111d;
                    dbpHigh = 74d;
                }else if (height >= 102 && height <= 104){
                    sbpLow = 90d;
                    dbpLow = 55d;
                    sbpHigh = 111d;
                    dbpHigh = 74d;
                }else if (height >= 105 && height <= 107){
                    sbpLow = 91d;
                    dbpLow = 55d;
                    sbpHigh = 113d;
                    dbpHigh = 74d;
                }else if (height >= 108 && height <= 110){
                    sbpLow = 92d;
                    dbpLow = 56d;
                    sbpHigh = 114d;
                    dbpHigh = 74d;
                }else if (height >= 111 && height <= 113){
                    sbpLow = 93d;
                    dbpLow = 56d;
                    sbpHigh = 115d;
                    dbpHigh = 74d;
                }else if (height >= 114 && height <= 116){
                    sbpLow = 94d;
                    dbpLow = 56d;
                    sbpHigh = 117d;
                    dbpHigh = 75d;
                }else if (height >= 117){
                    sbpLow = 95d;
                    dbpLow = 56d;
                    sbpHigh = 117d;
                    dbpHigh = 75d;
                }
            }else{ //女
                if (height < 101){
                    sbpLow = 89d;
                    dbpLow = 56d;
                    sbpHigh = 111d;
                    dbpHigh = 75d;
                }else if (height >= 101 && height <= 103){
                    sbpLow = 89d;
                    dbpLow = 56d;
                    sbpHigh = 111d;
                    dbpHigh = 75d;
                }else if (height >= 104 && height <= 106){
                    sbpLow = 90d;
                    dbpLow = 56d;
                    sbpHigh = 112d;
                    dbpHigh = 75d;
                }else if (height >= 107 && height <= 109){
                    sbpLow = 91d;
                    dbpLow = 56d;
                    sbpHigh = 113d;
                    dbpHigh = 75d;
                }else if (height >= 110 && height <= 112){
                    sbpLow = 92d;
                    dbpLow = 56d;
                    sbpHigh = 114d;
                    dbpHigh = 75d;
                }else if (height >= 113 && height <= 114){
                    sbpLow = 93d;
                    dbpLow = 56d;
                    sbpHigh = 115d;
                    dbpHigh = 76d;
                }else if (height >= 115){
                    sbpLow = 93d;
                    dbpLow = 56d;
                    sbpHigh = 115d;
                    dbpHigh = 76d;
                }
            }
        }else if (age == 5){
            if (sex == 1){  //男
                if (height < 109){
                    sbpLow = 92d;
                    dbpLow = 56d;
                    sbpHigh = 114d;
                    dbpHigh = 75d;
                }else if (height >= 109 && height <= 110){
                    sbpLow = 92d;
                    dbpLow = 56d;
                    sbpHigh = 114d;
                    dbpHigh = 75d;
                }else if (height >= 111 && height <= 113){
                    sbpLow = 93d;
                    dbpLow = 56d;
                    sbpHigh = 115d;
                    dbpHigh = 75d;
                }else if (height >= 114 && height <= 117){
                    sbpLow = 94d;
                    dbpLow = 57d;
                    sbpHigh = 117d;
                    dbpHigh = 76d;
                }else if (height >= 118 && height <= 120){
                    sbpLow = 95d;
                    dbpLow = 57d;
                    sbpHigh = 118d;
                    dbpHigh = 76d;
                }else if (height >= 121 && height <= 123){
                    sbpLow = 96d;
                    dbpLow = 58d;
                    sbpHigh = 119d;
                    dbpHigh = 77d;
                }else if (height >= 124){
                    sbpLow = 97d;
                    dbpLow = 58d;
                    sbpHigh = 120d;
                    dbpHigh = 77d;
                }
            }else {  //女
                if (height < 108){
                    sbpLow = 91d;
                    dbpLow = 56d;
                    sbpHigh = 113d;
                    dbpHigh = 76d;
                }else if (height >= 108 && height <= 109){
                    sbpLow = 91d;
                    dbpLow = 56d;
                    sbpHigh = 113d;
                    dbpHigh = 76d;
                }else if (height >= 110 && height <= 112){
                    sbpLow = 92d;
                    dbpLow = 56d;
                    sbpHigh = 114d;
                    dbpHigh = 76d;
                }else if (height >= 113 && height <= 116){
                    sbpLow = 93d;
                    dbpLow = 57d;
                    sbpHigh = 115d;
                    dbpHigh = 76d;
                }else if (height >= 117 && height <= 119){
                    sbpLow = 93d;
                    dbpLow = 57d;
                    sbpHigh = 116d;
                    dbpHigh = 77d;
                }else if (height >= 120 && height <= 122){
                    sbpLow = 94d;
                    dbpLow = 58d;
                    sbpHigh = 117d;
                    dbpHigh = 77d;
                }else if (height >= 123){
                    sbpLow = 95d;
                    dbpLow = 58d;
                    sbpHigh = 118d;
                    dbpHigh = 78d;
                }
            }
        }else if (age == 6){
            if (sex == 1){  //男
                if (height < 114){
                    sbpLow = 93d;
                    dbpLow = 57d;
                    sbpHigh = 115d;
                    dbpHigh = 76d;
                }else if (height >= 114 && height <= 116){
                    sbpLow = 94d;
                    dbpLow = 57d;
                    sbpHigh = 116d;
                    dbpHigh = 76d;
                }else if (height >= 117 && height <= 119){
                    sbpLow = 95d;
                    dbpLow = 58d;
                    sbpHigh = 117d;
                    dbpHigh = 77d;
                }else if (height >= 120 && height <= 123){
                    sbpLow = 96d;
                    dbpLow = 58d;
                    sbpHigh = 119d;
                    dbpHigh = 78d;
                }else if (height >= 124 && height <= 126){
                    sbpLow = 97d;
                    dbpLow = 59d;
                    sbpHigh = 120d;
                    dbpHigh = 78d;
                }else if (height >= 127 && height <= 129){
                    sbpLow = 98d;
                    dbpLow = 59d;
                    sbpHigh = 121d;
                    dbpHigh = 79d;
                }else if (height >= 130){
                    sbpLow = 99d;
                    dbpLow = 60d;
                    sbpHigh = 123d;
                    dbpHigh = 80d;
                }
            }else {  //女
                if (height < 113){
                    sbpLow = 92d;
                    dbpLow = 57d;
                    sbpHigh = 115d;
                    dbpHigh = 76d;
                }else if (height >= 113 && height <= 114){
                    sbpLow = 92d;
                    dbpLow = 57d;
                    sbpHigh = 115d;
                    dbpHigh = 77d;
                }else if (height >= 115 && height <= 118){
                    sbpLow = 93d;
                    dbpLow = 57d;
                    sbpHigh = 116d;
                    dbpHigh = 77d;
                }else if (height >= 119 && height <= 121){
                    sbpLow = 94d;
                    dbpLow = 58d;
                    sbpHigh = 117d;
                    dbpHigh = 78d;
                }else if (height >= 122 && height <= 125){
                    sbpLow = 95d;
                    dbpLow = 58d;
                    sbpHigh = 118d;
                    dbpHigh = 79d;
                }else if (height >= 126 && height <= 128){
                    sbpLow = 96d;
                    dbpLow = 59d;
                    sbpHigh = 119d;
                    dbpHigh = 79d;
                }else if (height >= 129){
                    sbpLow = 97d;
                    dbpLow = 59d;
                    sbpHigh = 121d;
                    dbpHigh = 80d;
                }
            }
        }else if (age == 7){
            if (sex == 1){ //男
                if (height < 118){
                    sbpLow = 94d;
                    dbpLow = 58d;
                    sbpHigh = 117d;
                    dbpHigh = 77d;
                }else if (height >= 118 && height <= 120){
                    sbpLow = 95d;
                    dbpLow = 58d;
                    sbpHigh = 118d;
                    dbpHigh = 78d;
                }else if (height >= 121 && height <= 123){
                    sbpLow = 96d;
                    dbpLow = 59d;
                    sbpHigh = 119d;
                    dbpHigh = 78d;
                }else if (height >= 124 && height <= 127){
                    sbpLow = 97d;
                    dbpLow = 59d;
                    sbpHigh = 120d;
                    dbpHigh = 79d;
                }else if (height >= 128 && height <= 131){
                    sbpLow = 98d;
                    dbpLow = 60d;
                    sbpHigh = 122d;
                    dbpHigh = 81d;
                }else if (height >= 132 && height <= 135){
                    sbpLow = 100d;
                    dbpLow = 61d;
                    sbpHigh = 124d;
                    dbpHigh = 82d;
                }else if (height >= 136){
                    sbpLow = 100d;
                    dbpLow = 62d;
                    sbpHigh = 125d;
                    dbpHigh = 82d;
                }
            }else {  //女
                if (height < 116){
                    sbpLow = 93d;
                    dbpLow = 57d;
                    sbpHigh = 115d;
                    dbpHigh = 77d;
                }else if (height >= 116 && height <= 118){
                    sbpLow = 93d;
                    dbpLow = 57d;
                    sbpHigh = 116d;
                    dbpHigh = 77d;
                }else if (height >= 119 && height <= 122){
                    sbpLow = 94d;
                    dbpLow = 58d;
                    sbpHigh = 117d;
                    dbpHigh = 78d;
                }else if (height >= 123 && height <= 126){
                    sbpLow = 95d;
                    dbpLow = 59d;
                    sbpHigh = 119d;
                    dbpHigh = 79d;
                }else if (height >= 127 && height <= 130){
                    sbpLow = 96d;
                    dbpLow = 59d;
                    sbpHigh = 120d;
                    dbpHigh = 80d;
                }else if (height >= 131 && height <= 133){
                    sbpLow = 97d;
                    dbpLow = 60d;
                    sbpHigh = 122d;
                    dbpHigh = 81d;
                }else if (height >= 134){
                    sbpLow = 98d;
                    dbpLow = 61d;
                    sbpHigh = 122d;
                    dbpHigh = 82d;
                }
            }
        }else if (age == 8){
            if (sex == 1){ //男
                if (height < 121){
                    sbpLow = 95d;
                    dbpLow = 59d;
                    sbpHigh = 118d;
                    dbpHigh = 78d;
                }else if (height >= 121 && height <= 123){
                    sbpLow = 95d;
                    dbpLow = 59d;
                    sbpHigh = 119d;
                    dbpHigh = 79d;
                }else if (height >= 124 && height <= 127){
                    sbpLow = 97d;
                    dbpLow = 60d;
                    sbpHigh = 120d;
                    dbpHigh = 80d;
                }else if (height >= 128 && height <= 132){
                    sbpLow = 98d;
                    dbpLow = 61d;
                    sbpHigh = 122d;
                    dbpHigh = 81d;
                }else if (height >= 133 && height <= 136){
                    sbpLow = 99d;
                    dbpLow = 62d;
                    sbpHigh = 124d;
                    dbpHigh = 82d;
                }else if (height >= 137 && height <= 139){
                    sbpLow = 101d;
                    dbpLow = 62d;
                    sbpHigh = 125d;
                    dbpHigh = 83d;
                }else if (height >= 140){
                    sbpLow = 102d;
                    dbpLow = 63d;
                    sbpHigh = 127d;
                    dbpHigh = 84d;
                }
            }else {  //女
                if (height < 120){
                    sbpLow = 94d;
                    dbpLow = 58d;
                    sbpHigh = 116d;
                    dbpHigh = 78d;
                }else if (height >= 120 && height <= 122){
                    sbpLow = 94d;
                    dbpLow = 58d;
                    sbpHigh = 117d;
                    dbpHigh = 79d;
                }else if (height >= 123 && height <= 126){
                    sbpLow = 95d;
                    dbpLow = 59d;
                    sbpHigh = 119d;
                    dbpHigh = 79d;
                }else if (height >= 127 && height <= 131){
                    sbpLow = 96d;
                    dbpLow = 60d;
                    sbpHigh = 120d;
                    dbpHigh = 80d;
                }else if (height >= 132 && height <= 135){
                    sbpLow = 98d;
                    dbpLow = 61d;
                    sbpHigh = 122d;
                    dbpHigh = 82d;
                }else if (height >= 136 && height <= 138){
                    sbpLow = 99d;
                    dbpLow = 61d;
                    sbpHigh = 123d;
                    dbpHigh = 83d;
                }else if (height >= 139){
                    sbpLow = 100d;
                    dbpLow = 62d;
                    sbpHigh = 124d;
                    dbpHigh = 83d;
                }
            }
        }else if (age == 9){
            if (sex == 1){ //男
                if (height < 125){
                    sbpLow = 96d;
                    dbpLow = 60d;
                    sbpHigh = 119d;
                    dbpHigh = 80d;
                }else if (height >= 125 && height <= 128){
                    sbpLow = 96d;
                    dbpLow = 60d;
                    sbpHigh = 120d;
                    dbpHigh = 80d;
                }else if (height >= 129 && height <= 132){
                    sbpLow = 98d;
                    dbpLow = 61d;
                    sbpHigh = 122d;
                    dbpHigh = 82d;
                }else if (height >= 133 && height <= 137){
                    sbpLow = 99d;
                    dbpLow = 62d;
                    sbpHigh = 124d;
                    dbpHigh = 83d;
                }else if (height >= 138 && height <= 142){
                    sbpLow = 101d;
                    dbpLow = 63d;
                    sbpHigh = 126d;
                    dbpHigh = 84d;
                }else if (height >= 143 && height <= 145){
                    sbpLow = 102d;
                    dbpLow = 64d;
                    sbpHigh = 128d;
                    dbpHigh = 85d;
                }else if (height >= 146){
                    sbpLow = 103d;
                    dbpLow = 64d;
                    sbpHigh = 129d;
                    dbpHigh = 85d;
                }
            }else {  //女
                if (height < 124){
                    sbpLow = 95d;
                    dbpLow = 59d;
                    sbpHigh = 118d;
                    dbpHigh = 79d;
                }else if (height >= 124 && height <= 127){
                    sbpLow = 95d;
                    dbpLow = 59d;
                    sbpHigh = 119d;
                    dbpHigh = 80d;
                }else if (height >= 128 && height <= 132){
                    sbpLow = 97d;
                    dbpLow = 60d;
                    sbpHigh = 120d;
                    dbpHigh = 81d;
                }else if (height >= 133 && height <= 136){
                    sbpLow = 98d;
                    dbpLow = 61d;
                    sbpHigh = 122d;
                    dbpHigh = 82d;
                }else if (height >= 137 && height <= 141){
                    sbpLow = 100d;
                    dbpLow = 62d;
                    sbpHigh = 124d;
                    dbpHigh = 84d;
                }else if (height >= 142 && height <= 145){
                    sbpLow = 101d;
                    dbpLow = 63d;
                    sbpHigh = 125d;
                    dbpHigh = 84d;
                }else if (height >= 146){
                    sbpLow = 102d;
                    dbpLow = 63d;
                    sbpHigh = 126d;
                    dbpHigh = 85d;
                }
            }
        }else if (age == 10){
            if (sex == 1){ //男
                if (height < 130){
                    sbpLow = 97d;
                    dbpLow = 61d;
                    sbpHigh = 121d;
                    dbpHigh = 81d;
                }else if (height >= 130 && height <= 132){
                    sbpLow = 98d;
                    dbpLow = 62d;
                    sbpHigh = 122d;
                    dbpHigh = 82d;
                }else if (height >= 133 && height <= 137){
                    sbpLow = 99d;
                    dbpLow = 62d;
                    sbpHigh = 124d;
                    dbpHigh = 83d;
                }else if (height >= 138 && height <= 142){
                    sbpLow = 101d;
                    dbpLow = 63d;
                    sbpHigh = 126d;
                    dbpHigh = 85d;
                }else if (height >= 143 && height <= 147){
                    sbpLow = 102d;
                    dbpLow = 64d;
                    sbpHigh = 128d;
                    dbpHigh = 85d;
                }else if (height >= 148 && height <= 151){
                    sbpLow = 104d;
                    dbpLow = 64d;
                    sbpHigh = 130d;
                    dbpHigh = 86d;
                }else if (height >= 152){
                    sbpLow = 105d;
                    dbpLow = 64d;
                    sbpHigh = 131d;
                    dbpHigh = 86d;
                }
            }else {  //女
                if (height < 130){
                    sbpLow = 96d;
                    dbpLow = 60d;
                    sbpHigh = 120d;
                    dbpHigh = 81d;
                }else if (height >= 130 && height <= 133){
                    sbpLow = 97d;
                    dbpLow = 61d;
                    sbpHigh = 121d;
                    dbpHigh = 82d;
                }else if (height >= 134 && height <= 138){
                    sbpLow = 99d;
                    dbpLow = 62d;
                    sbpHigh = 123d;
                    dbpHigh = 83d;
                }else if (height >= 139 && height <= 143){
                    sbpLow = 100d;
                    dbpLow = 63d;
                    sbpHigh = 124d;
                    dbpHigh = 84d;
                }else if (height >= 144 && height <= 147){
                    sbpLow = 101d;
                    dbpLow = 63d;
                    sbpHigh = 126d;
                    dbpHigh = 85d;
                }else if (height >= 148 && height <= 151){
                    sbpLow = 103d;
                    dbpLow = 63d;
                    sbpHigh = 128d;
                    dbpHigh = 85d;
                }else if (height >= 152){
                    sbpLow = 103d;
                    dbpLow = 64d;
                    sbpHigh = 129d;
                    dbpHigh = 86d;
                }
            }
        }else if (age == 11){
            if (sex == 1){ //男
                if (height < 134){
                    sbpLow = 98d;
                    dbpLow = 62d;
                    sbpHigh = 122d;
                    dbpHigh = 83d;
                }else if (height >= 134 && height <= 137){
                    sbpLow = 99d;
                    dbpLow = 63d;
                    sbpHigh = 124d;
                    dbpHigh = 84d;
                }else if (height >= 138 && height <= 142){
                    sbpLow = 100d;
                    dbpLow = 64d;
                    sbpHigh = 126d;
                    dbpHigh = 85d;
                }else if (height >= 143 && height <= 148){
                    sbpLow = 102d;
                    dbpLow = 64d;
                    sbpHigh = 128d;
                    dbpHigh = 86d;
                }else if (height >= 149 && height <= 153){
                    sbpLow = 104d;
                    dbpLow = 64d;
                    sbpHigh = 130d;
                    dbpHigh = 86d;
                }else if (height >= 154 && height <= 157){
                    sbpLow = 106d;
                    dbpLow = 64d;
                    sbpHigh = 132d;
                    dbpHigh = 86d;
                }else if (height >= 158){
                    sbpLow = 106d;
                    dbpLow = 64d;
                    sbpHigh = 133d;
                    dbpHigh = 86d;
                }
            }else {  //女
                if (height < 136){
                    sbpLow = 98d;
                    dbpLow = 62d;
                    sbpHigh = 122d;
                    dbpHigh = 83d;
                }else if (height >= 136 && height <= 139){
                    sbpLow = 99d;
                    dbpLow = 62d;
                    sbpHigh = 123d;
                    dbpHigh = 84d;
                }else if (height >= 140 && height <= 144){
                    sbpLow = 101d;
                    dbpLow = 63d;
                    sbpHigh = 125d;
                    dbpHigh = 85d;
                }else if (height >= 145 && height <= 149){
                    sbpLow = 102d;
                    dbpLow = 64d;
                    sbpHigh = 127d;
                    dbpHigh = 86d;
                }else if (height >= 150 && height <= 154){
                    sbpLow = 103d;
                    dbpLow = 64d;
                    sbpHigh = 128d;
                    dbpHigh = 86d;
                }else if (height >= 155 && height <= 157){
                    sbpLow = 104d;
                    dbpLow = 64d;
                    sbpHigh = 129d;
                    dbpHigh = 86d;
                }else if (height >= 158){
                    sbpLow = 104d;
                    dbpLow = 64d;
                    sbpHigh = 130d;
                    dbpHigh = 86d;
                }
            }
        }else if (age == 12){
            if (sex == 1){ //男
                if (height < 140){
                    sbpLow = 100d;
                    dbpLow = 64d;
                    sbpHigh = 125d;
                    dbpHigh = 85d;
                }else if (height >= 140 && height <= 144){
                    sbpLow = 101d;
                    dbpLow = 64d;
                    sbpHigh = 126d;
                    dbpHigh = 86d;
                }else if (height >= 145 && height <= 149){
                    sbpLow = 102d;
                    dbpLow = 65d;
                    sbpHigh = 128d;
                    dbpHigh = 86d;
                }else if (height >= 150 && height <= 155){
                    sbpLow = 104d;
                    dbpLow = 65d;
                    sbpHigh = 131d;
                    dbpHigh = 86d;
                }else if (height >= 156 && height <= 160){
                    sbpLow = 106d;
                    dbpLow = 65d;
                    sbpHigh = 133d;
                    dbpHigh = 86d;
                }else if (height >= 161 && height <= 164){
                    sbpLow = 108d;
                    dbpLow = 65d;
                    sbpHigh = 135d;
                    dbpHigh = 87d;
                }else if (height >= 165){
                    sbpLow = 108d;
                    dbpLow = 65d;
                    sbpHigh = 136d;
                    dbpHigh = 87d;
                }
            }else {  //女
                if (height < 142){
                    sbpLow = 100d;
                    dbpLow = 63d;
                    sbpHigh = 124d;
                    dbpHigh = 85d;
                }else if (height >= 142 && height <= 145){
                    sbpLow = 101d;
                    dbpLow = 63d;
                    sbpHigh = 125d;
                    dbpHigh = 85d;
                }else if (height >= 146 && height <= 150){
                    sbpLow = 102d;
                    dbpLow = 64d;
                    sbpHigh = 127d;
                    dbpHigh = 86d;
                }else if (height >= 151 && height <= 154){
                    sbpLow = 103d;
                    dbpLow = 64d;
                    sbpHigh = 129d;
                    dbpHigh = 86d;
                }else if (height >= 155 && height <= 158){
                    sbpLow = 104d;
                    dbpLow = 64d;
                    sbpHigh = 130d;
                    dbpHigh = 87d;
                }else if (height >= 159 && height <= 162){
                    sbpLow = 105d;
                    dbpLow = 64d;
                    sbpHigh = 130d;
                    dbpHigh = 87d;
                }else if (height >= 163){
                    sbpLow = 105d;
                    dbpLow = 64d;
                    sbpHigh = 131d;
                    dbpHigh = 87d;
                }
            }
        }else if (age == 13){
            if (sex == 1){ //男
                if (height < 147){
                    sbpLow = 102d;
                    dbpLow = 65d;
                    sbpHigh = 128d;
                    dbpHigh = 86d;
                }else if (height >= 147 && height <= 151){
                    sbpLow = 103d;
                    dbpLow = 65d;
                    sbpHigh = 129d;
                    dbpHigh = 87d;
                }else if (height >= 152 && height <= 156){
                    sbpLow = 104d;
                    dbpLow = 65d;
                    sbpHigh = 131d;
                    dbpHigh = 87d;
                }else if (height >= 157 && height <= 162){
                    sbpLow = 106d;
                    dbpLow = 65d;
                    sbpHigh = 133d;
                    dbpHigh = 87d;
                }else if (height >= 163 && height <= 167){
                    sbpLow = 108d;
                    dbpLow = 65d;
                    sbpHigh = 136d;
                    dbpHigh = 87d;
                }else if (height >= 168 && height <= 171){
                    sbpLow = 110d;
                    dbpLow = 66d;
                    sbpHigh = 138d;
                    dbpHigh = 87d;
                }else if (height >= 172){
                    sbpLow = 110d;
                    dbpLow = 66d;
                    sbpHigh = 139d;
                    dbpHigh = 88d;
                }
            }else {  //女
                if (height < 147){
                    sbpLow = 101d;
                    dbpLow = 64d;
                    sbpHigh = 126d;
                    dbpHigh = 86d;
                }else if (height >= 147 && height <= 149){
                    sbpLow = 102d;
                    dbpLow = 64d;
                    sbpHigh = 127d;
                    dbpHigh = 87d;
                }else if (height >= 150 && height <= 153){
                    sbpLow = 103d;
                    dbpLow = 64d;
                    sbpHigh = 128d;
                    dbpHigh = 87d;
                }else if (height >= 154 && height <= 157){
                    sbpLow = 104d;
                    dbpLow = 65d;
                    sbpHigh = 129d;
                    dbpHigh = 87d;
                }else if (height >= 158 && height <= 161){
                    sbpLow = 105d;
                    dbpLow = 65d;
                    sbpHigh = 130d;
                    dbpHigh = 87d;
                }else if (height >= 162 && height <= 164){
                    sbpLow = 105d;
                    dbpLow = 65d;
                    sbpHigh = 131d;
                    dbpHigh = 87d;
                }else if (height >= 165){
                    sbpLow = 105d;
                    dbpLow = 65d;
                    sbpHigh = 131d;
                    dbpHigh = 87d;
                }
            }
        }else if (age == 14){
            if (sex == 1){ //男
                if (height < 154){
                    sbpLow = 103d;
                    dbpLow = 65d;
                    sbpHigh = 130d;
                    dbpHigh = 87d;
                }else if (height >= 154 && height <= 157){
                    sbpLow = 104d;
                    dbpLow = 65d;
                    sbpHigh = 132d;
                    dbpHigh = 87d;
                }else if (height >= 158 && height <= 162){
                    sbpLow = 106d;
                    dbpLow = 65d;
                    sbpHigh = 133d;
                    dbpHigh = 87d;
                }else if (height >= 163 && height <= 167){
                    sbpLow = 108d;
                    dbpLow = 65d;
                    sbpHigh = 136d;
                    dbpHigh = 87d;
                }else if (height >= 168 && height <= 172){
                    sbpLow = 109d;
                    dbpLow = 66d;
                    sbpHigh = 138d;
                    dbpHigh = 88d;
                }else if (height >= 173 && height <= 176){
                    sbpLow = 111d;
                    dbpLow = 66d;
                    sbpHigh = 140d;
                    dbpHigh = 88d;
                }else if (height >= 177){
                    sbpLow = 112d;
                    dbpLow = 67d;
                    sbpHigh = 141d;
                    dbpHigh = 89d;
                }
            }else {  //女
                if (height < 149){
                    sbpLow = 102d;
                    dbpLow = 65d;
                    sbpHigh = 127d;
                    dbpHigh = 87d;
                }else if (height >= 149 && height <= 152){
                    sbpLow = 103d;
                    dbpLow = 65d;
                    sbpHigh = 128d;
                    dbpHigh = 87d;
                }else if (height >= 153 && height <= 155){
                    sbpLow = 104d;
                    dbpLow = 65d;
                    sbpHigh = 129d;
                    dbpHigh = 87d;
                }else if (height >= 156 && height <= 159){
                    sbpLow = 104d;
                    dbpLow = 65d;
                    sbpHigh = 130d;
                    dbpHigh = 87d;
                }else if (height >= 160 && height <= 163){
                    sbpLow = 105d;
                    dbpLow = 65d;
                    sbpHigh = 130d;
                    dbpHigh = 87d;
                }else if (height >= 164 && height <= 166){
                    sbpLow = 105d;
                    dbpLow = 65d;
                    sbpHigh = 131d;
                    dbpHigh = 87d;
                }else if (height >= 167){
                    sbpLow = 106d;
                    dbpLow = 65d;
                    sbpHigh = 131d;
                    dbpHigh = 88d;
                }
            }
        }else if (age == 15){
            if (sex == 1){ //男
                if (height < 158){
                    sbpLow = 105d;
                    dbpLow = 65d;
                    sbpHigh = 132d;
                    dbpHigh = 87d;
                }else if (height >= 158 && height <= 161){
                    sbpLow = 106d;
                    dbpLow = 65d;
                    sbpHigh = 133d;
                    dbpHigh = 87d;
                }else if (height >= 162 && height <= 166){
                    sbpLow = 107d;
                    dbpLow = 66d;
                    sbpHigh = 135d;
                    dbpHigh = 88d;
                }else if (height >= 167 && height <= 170){
                    sbpLow = 109d;
                    dbpLow = 66d;
                    sbpHigh = 137d;
                    dbpHigh = 88d;
                }else if (height >= 171 && height <= 174){
                    sbpLow = 110d;
                    dbpLow = 66d;
                    sbpHigh = 139d;
                    dbpHigh = 89d;
                }else if (height >= 175 && height <= 178){
                    sbpLow = 112d;
                    dbpLow = 67d;
                    sbpHigh = 141d;
                    dbpHigh = 89d;
                }else if (height >= 179){
                    sbpLow = 113d;
                    dbpLow = 67d;
                    sbpHigh = 142d;
                    dbpHigh = 90d;
                }
            }else {  //女
                if (height < 151){
                    sbpLow = 103d;
                    dbpLow = 65d;
                    sbpHigh = 128d;
                    dbpHigh = 87d;
                }else if (height >= 151 && height <= 152){
                    sbpLow = 103d;
                    dbpLow = 65d;
                    sbpHigh = 128d;
                    dbpHigh = 88d;
                }else if (height >= 153 && height <= 156){
                    sbpLow = 104d;
                    dbpLow = 65d;
                    sbpHigh = 129d;
                    dbpHigh = 88d;
                }else if (height >= 157 && height <= 160){
                    sbpLow = 105d;
                    dbpLow = 65d;
                    sbpHigh = 130d;
                    dbpHigh = 88d;
                }else if (height >= 161 && height <= 163){
                    sbpLow = 105d;
                    dbpLow = 65d;
                    sbpHigh = 131d;
                    dbpHigh = 88d;
                }else if (height >= 164 && height <= 166){
                    sbpLow = 105d;
                    dbpLow = 65d;
                    sbpHigh = 131d;
                    dbpHigh = 88d;
                }else if (height >= 167){
                    sbpLow = 106d;
                    dbpLow = 65d;
                    sbpHigh = 131d;
                    dbpHigh = 88d;
                }
            }
        }else if (age == 16){
            if (sex == 1){ //男
                if (height < 161){
                    sbpLow = 105d;
                    dbpLow = 66d;
                    sbpHigh = 133d;
                    dbpHigh = 88d;
                }else if (height >= 161 && height <= 164){
                    sbpLow = 106d;
                    dbpLow = 66d;
                    sbpHigh = 134d;
                    dbpHigh = 88d;
                }else if (height >= 165 && height <= 168){
                    sbpLow = 107d;
                    dbpLow = 66d;
                    sbpHigh = 136d;
                    dbpHigh = 88d;
                }else if (height >= 169 && height <= 172){
                    sbpLow = 109d;
                    dbpLow = 66d;
                    sbpHigh = 138d;
                    dbpHigh = 88d;
                }else if (height >= 173 && height <= 176){
                    sbpLow = 111d;
                    dbpLow = 67d;
                    sbpHigh = 140d;
                    dbpHigh = 89d;
                }else if (height >= 177 && height <= 179){
                    sbpLow = 112d;
                    dbpLow = 67d;
                    sbpHigh = 141d;
                    dbpHigh = 90d;
                }else if (height >= 180){
                    sbpLow = 113d;
                    dbpLow = 67d;
                    sbpHigh = 142d;
                    dbpHigh = 90d;
                }
            }else {  //女
                if (height < 151){
                    sbpLow = 103d;
                    dbpLow = 65d;
                    sbpHigh = 128d;
                    dbpHigh = 88d;
                }else if (height >= 151 && height <= 153){
                    sbpLow = 103d;
                    dbpLow = 65d;
                    sbpHigh = 129d;
                    dbpHigh = 88d;
                }else if (height >= 154 && height <= 157){
                    sbpLow = 104d;
                    dbpLow = 65d;
                    sbpHigh = 130d;
                    dbpHigh = 88d;
                }else if (height >= 158 && height <= 160){
                    sbpLow = 105d;
                    dbpLow = 65d;
                    sbpHigh = 130d;
                    dbpHigh = 88d;
                }else if (height >= 161 && height <= 164){
                    sbpLow = 105d;
                    dbpLow = 66d;
                    sbpHigh = 131d;
                    dbpHigh = 88d;
                }else if (height >= 165 && height <= 167){
                    sbpLow = 106d;
                    dbpLow = 66d;
                    sbpHigh = 131d;
                    dbpHigh = 88d;
                }else if (height >= 168){
                    sbpLow = 106d;
                    dbpLow = 66d;
                    sbpHigh = 132d;
                    dbpHigh = 88d;
                }
            }
        }else if (age == 17){
            if (sex == 1){ //男
                if (height < 163){
                    sbpLow = 106d;
                    dbpLow = 66d;
                    sbpHigh = 134d;
                    dbpHigh = 88d;
                }else if (height >= 163 && height <= 165){
                    sbpLow = 107d;
                    dbpLow = 66d;
                    sbpHigh = 135d;
                    dbpHigh = 88d;
                }else if (height >= 166 && height <= 169){
                    sbpLow = 108d;
                    dbpLow = 66d;
                    sbpHigh = 136d;
                    dbpHigh = 88d;
                }else if (height >= 170 && height <= 173){
                    sbpLow = 109d;
                    dbpLow = 67d;
                    sbpHigh = 138d;
                    dbpHigh = 89d;
                }else if (height >= 174 && height <= 177){
                    sbpLow = 111d;
                    dbpLow = 67d;
                    sbpHigh = 140d;
                    dbpHigh = 89d;
                }else if (height >= 178 && height <= 180){
                    sbpLow = 112d;
                    dbpLow = 67d;
                    sbpHigh = 142d;
                    dbpHigh = 90d;
                }else if (height >= 181){
                    sbpLow = 113d;
                    dbpLow = 68d;
                    sbpHigh = 143d;
                    dbpHigh = 90d;
                }
            }else {  //女
                if (height < 152){
                    sbpLow = 103d;
                    dbpLow = 66d;
                    sbpHigh = 129d;
                    dbpHigh = 88d;
                }else if (height >= 152 && height <= 154){
                    sbpLow = 104d;
                    dbpLow = 66d;
                    sbpHigh = 129d;
                    dbpHigh = 89d;
                }else if (height >= 155 && height <= 157){
                    sbpLow = 104d;
                    dbpLow = 66d;
                    sbpHigh = 130d;
                    dbpHigh = 89d;
                }else if (height >= 158 && height <= 161){
                    sbpLow = 105d;
                    dbpLow = 66d;
                    sbpHigh = 130d;
                    dbpHigh = 89d;
                }else if (height >= 162 && height <= 164){
                    sbpLow = 105d;
                    dbpLow = 66d;
                    sbpHigh = 131d;
                    dbpHigh = 89d;
                }else if (height >= 165 && height <= 167){
                    sbpLow = 106d;
                    dbpLow = 66d;
                    sbpHigh = 132d;
                    dbpHigh = 89d;
                }else if (height >=168){
                    sbpLow = 106d;
                    dbpLow = 66d;
                    sbpHigh = 132d;
                    dbpHigh = 89d;
                }
            }
        }

        resultMap.put("sbpLow",sbpLow);
        resultMap.put("sbpHigh",sbpHigh);
        resultMap.put("dbpLow",dbpLow);
        resultMap.put("dbpHigh",dbpHigh);

        return resultMap;
    }

}
