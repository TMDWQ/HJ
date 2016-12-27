package com.micro.android316.housekeeping.utils;

import android.os.Handler;
import android.util.Log;

import com.micro.android316.housekeeping.model.Search;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 张文 on 2016/12/27.
 */

public class SimpleSearchTools {


    private static final String OLD[] = {"老人护理", "老", "lao", "laorenhuli", "old"};
    private static final String CHILD[] = {"婴幼儿护理", "婴幼儿", "yinyouer", "yin you er"};
    private static final String JIATING[] = {"家庭", "家居", "jia ting", "jiating", "家居保洁"};
    private static final String HOMEJU[] = {"家具", "jia ju", "jiaju", "家具保洁"};
    private static final String PENGREN[] = {"烹饪", "料理", "煮饭", "peng ren", "peng", "pengren", "zhufan", "zhu fan", "liaoli", "niaoli", "liao li", "niao li"};
    private String wordKey;
    private boolean b = false;
    private static final String s = "赵 钱 孙 李 周 吴 郑 王" +
            "冯 陈 楮 卫 蒋 沈 韩 杨" +
            "朱 秦 尤 许 何 吕 施 张" +
            "孔 曹 严 华 金 魏 陶 姜" +
            "戚 谢 邹 喻 柏 水 窦 章" +
            "云 苏 潘 葛 奚 范 彭 郎" +
            "鲁 韦 昌 马 苗 凤 花 方" +
            "俞 任 袁 柳 酆 鲍 史 唐" +
            "费 廉 岑 薛 雷 贺 倪 汤" +
            "滕 殷 罗 毕 郝 邬 安 常" +
            "乐 于 时 傅 皮 卞 齐 康" +
            "伍 余 元 卜 顾 孟 平 黄" +
            "和 穆 萧 尹 姚 邵 湛 汪" +
            "祁 毛 禹 狄 米 贝 明 臧" +
            "计 伏 成 戴 谈 宋 茅 庞" +
            "熊 纪 舒 屈 项 祝 董 梁" +
            "杜 阮 蓝 闽 席 季 麻 强" +
            "贾 路 娄 危 江 童 颜 郭" +
            "梅 盛 林 刁 锺 徐 丘 骆" +
            "高 夏 蔡 田 樊 胡 凌 霍" +
            "虞 万 支 柯 昝 管 卢 莫" +
            "经 房 裘 缪 干 解 应 宗" +
            "丁 宣 贲 邓 郁 单 杭 洪" +
            "包 诸 左 石 崔 吉 钮 龚" +
            "程 嵇 邢 滑 裴 陆 荣 翁" +
            "荀 羊 於 惠 甄 麹 家 封" +
            "芮 羿 储 靳 汲 邴 糜 松" +
            "井 段 富 巫 乌 焦 巴 弓" +
            "牧 隗 山 谷 车 侯 宓 蓬" +
            "全 郗 班 仰 秋 仲 伊 宫" +
            "宁 仇 栾 暴 甘 斜 厉 戎" +
            "祖 武 符 刘 景 詹 束 龙" +
            "叶 幸 司 韶 郜 黎 蓟 薄" +
            "印 宿 白 怀 蒲 邰 从 鄂" +
            "索 咸 籍 赖 卓 蔺 屠 蒙" +
            "池 乔 阴 郁 胥 能 苍 双" +
            "闻 莘 党 翟 谭 贡 劳 逄" +
            "姬 申 扶 堵 冉 宰 郦 雍" +
            "郤 璩 桑 桂 濮 牛 寿 通" +
            "边 扈 燕 冀 郏 浦 尚 农" +
            "温 别 庄 晏 柴 瞿 阎 充" +
            "慕 连 茹 习 宦 艾 鱼 容" +
            "向 古 易 慎 戈 廖 庾 终" +
            "暨 居 衡 步 都 耿 满 弘" +
            "匡 国 文 寇 广 禄 阙 东" +
            "欧 殳 沃 利 蔚 越 夔 隆" +
            "师 巩 厍 聂 晁 勾 敖 融" +
            "冷 訾 辛 阚 那 简 饶 空" +
            "曾 毋 沙 乜 养 鞠 须 丰" +
            "巢 关 蒯 相 查 后 荆 红" +
            "游 竺 权 逑 盖 益 桓 公" +
            "万俟 司马 上官 欧阳" +
            "夏侯 诸葛 闻人 东方" +
            "赫连 皇甫 尉迟 公羊" +
            "澹台 公冶 宗政 濮阳" +
            "淳于 单于 太叔 申屠" +
            "公孙 仲孙 轩辕 令狐" +
            "锺离 宇文 长孙 慕容" +
            "鲜于 闾丘 司徒 司空" +
            "丌官 司寇 仉 督 子车" +
            "颛孙 端木 巫马 公西" +
            "漆雕 乐正 壤驷 公良" +
            "拓拔 夹谷 宰父 谷梁" +
            "晋 楚 阎 法 汝 鄢 涂 钦" +
            "段干 百里 东郭 南门" +
            "呼延 归 海 羊舌 微生" +
            "岳 帅 缑 亢 况 后 有 琴" +
            "梁丘 左丘 东门 西门" +
            "商 牟 佘 佴 伯 赏 南宫" +
            "墨 哈 谯 笪 年 爱 阳 佟";
    private String URL = "http://139.199.196.199/android/index.php/home/index/search?name=";
    private String URL_TYPE = "http://139.199.196.199/android/index.php/home/index/getnannysinfomation?type=";
    private List<Search> list = new ArrayList<>();

    /**
     * 百家姓
     */

    public SimpleSearchTools(String wordKey) {
        this.wordKey = wordKey;
    }

    public SimpleSearchTools() {

    }

    private String[] getSurname() {
        String[] ss = s.split(" ");
        return ss;
    }

    public boolean isName(String str) {
        if (str.length() == 0 || str == null) {
            return false;
        }
        String c = str.charAt(0) + "";
        for (String i : getSurname()) {
            if (isInclude(i, c)) {
                Log.i("ooo", "true");
                return true;
            }
        }
        return false;
    }

    /**
     * 首先搜索按名字
     * 其次搜索按类型
     * 最后所有全部
     */
    public boolean search(Handler handler, int request) {
        if (getType() != -1) {
            searchForType(handler, request);
            return true;
        }
        if (isName(wordKey)) {
            searchForName(handler, request);
            return b;
        }
        return false;
    }


    private void searchForName(final Handler handler, final int request) {
        b=false;
        String name = null;
        try {
            name = URLEncoder.encode(wordKey, "utf-8");
            HttpTools tools = new HttpTools(URL + name) {
                @Override
                public void post(String line) {
                    try {
                        JSONObject object = new JSONObject(line);
                        if (object.getString("code").equals("1")) {
                            b = true;
                            list.clear();
                            JSONArray array = object.getJSONArray("info");
                            for (int i = 0; i < array.length(); i++) {
                                object = array.getJSONObject(i);
                                Search search = new Search();
                                search.setId(object.getString("id"));
                                search.setStars((float) object.getDouble("stars"));
                                search.setType(object.getString("type"));
                                search.setName(object.getString("name"));
                                list.add(search);

                            }
                            handler.sendEmptyMessage(request);


                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            };
            tools.runForGet();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }



    }
    private void searchForType(final Handler handler, final int request){
       int i=getType();
       if(i!=-1){
           HttpTools tools=new HttpTools(URL_TYPE+i) {
               @Override
               public void post(String line) {
                   try {
                       JSONObject object=new JSONObject(line);
                       if(object.getString("code").equals("1")){
                           list.clear();
                           JSONArray array=object.getJSONArray("info");
                           for(int i=0;i<array.length();i++){
                               object=array.getJSONObject(i);
                               Search search=new Search();
                               search.setId(object.getString("id"));
                               search.setStars((float) object.getDouble("stars"));
                               search.setType(object.getString("type"));
                               search.setName(object.getString("name"));
                               list.add(search);
                           }
                           handler.sendEmptyMessage(request);

                       }
                   } catch (JSONException e) {
                       e.printStackTrace();
                   }
               }
           };
           tools.runForGet();
       }
    }

    private int getType(){
        int type=-1;
        for(String i:OLD){
            if(isInclude(wordKey,i)){
                type=1;
            }
        }
        for(String i:CHILD){
            if(isInclude(wordKey,i)){
                type=2;
            }
        }
        for(String i:JIATING){
            if(isInclude(wordKey,i)){
                type=3;
            }
        }
        for(String i:HOMEJU){
            if(isInclude(wordKey,i)){
                type=4;
            }
        }
        for(String i:PENGREN){
            if(isInclude(wordKey,i)){
                type=5;
            }
        }
        return type;
    }

    private boolean isInclude(String a,String b){
        if(a.length()<b.length()){
            return false;
        }
        if(a.length()==b.length()){
            if(a.equals(b)){
                return true;
            }
        }
        for(int i=0;i<a.length();i++){
            String s="";
            for(int j=0;j<b.length();j++){
                char c=a.charAt(j);
                s+=c;
            }
            if(s.equals(b)){
                return true;
            }
        }
        return false;
    }


    public List<Search> getList() {
        return list;
    }

    public void setWordKey(String wordKey) {
        this.wordKey = wordKey;
    }
}
