package com.guoku.guokuv4.bean;


import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;


/**
 * zhangyao
 * zhangyao@guoku.com
 * 15/9/11 14:09
 */
public class Entities {


    /**
     * entity : {"brand":"","category_id":"159","chief_image":"http://imgcdn.guoku.com/images/104cee19866f0e0a3cd19bfd9fad5b33.jpg","created_time":1441886619,"creator_id":195580,"detail_images":["http://imgcdn.guoku.com/images/5e8b6b0e68c572eda7db8e517eb4057c.jpg","http://imgcdn.guoku.com/images/1bef6421e62b42a575285b0402d674d2.jpg"],"entity_hash":"833257c4","entity_id":1546740,"intro":"","item_id_list":["54c21867a2128a0711d970da"],"item_list":[{"buy_link":"http://api.guoku.com/mobile/v4/item/521838447655/?type=mobile","cid":"50002766","default":"True","entity_id":"1546740","foreign_price":"0.00","id":"321219","origin_id":"521838447655","origin_source":"taobao.com","price":19,"rank":"0","status":"2","volume":"0"}]}
     */

    private EntityEntity entity;
    /**
     * note : {"creator":{"avatar_large":"http://imgcdn.guoku.com/avatar/large_14_5024e58479be25f0e253035cd77cbf3d.png","avatar_small":"http://imgcdn.guoku.com/avatar/large_14_5024e58479be25f0e253035cd77cbf3d.png","bio":"果库谁人不识货","city":"卢森堡","email":"aki.liao@gmail.com","entity_note_count":3218,"fan_count":951,"following_count":19,"gender":"F","is_active":"2","is_censor":false,"like_count":736,"location":"海外","nickname":"亚飞","relation":0,"tag_count":212,"user_id":14,"website":""}}
     */

    private NoteEntity note;

    public void setEntity(EntityEntity entity) {
        this.entity = entity;
    }

    public EntityEntity getEntity() {
        return entity;
    }

    public void setNote(NoteEntity note) {
        this.note = note;
    }

    public NoteEntity getNote() {
        return note;
    }

    public static class EntityEntity {


        /**
         * brand :
         * category_id : 159
         * chief_image : http://imgcdn.guoku.com/images/104cee19866f0e0a3cd19bfd9fad5b33.jpg
         * created_time : 1441886619
         * creator_id : 195580
         * detail_images : ["http://imgcdn.guoku.com/images/5e8b6b0e68c572eda7db8e517eb4057c.jpg","http://imgcdn.guoku.com/images/1bef6421e62b42a575285b0402d674d2.jpg"]
         * entity_hash : 833257c4
         * entity_id : 1546740
         * intro :
         * item_id_list : ["54c21867a2128a0711d970da"]
         * item_list : [{"buy_link":"http://api.guoku.com/mobile/v4/item/521838447655/?type=mobile","cid":"50002766","default":"True","entity_id":"1546740","foreign_price":"0.00","id":"321219","origin_id":"521838447655","origin_source":"taobao.com","price":19,"rank":"0","status":"2","volume":"0"}]
         */

        private ArrayList<Object> poker_id_list;
        private String brand;
        private String category_id;
        private String chief_image;
        private int created_time;
        private int creator_id;
        private String entity_hash;
        private int entity_id;
        private String intro;
        private List<String> detail_images;
        private List<String> item_id_list;
        private List<ItemListEntity> item_list;

        public void setBrand(String brand) {
            this.brand = brand;
        }

        public void setCategory_id(String category_id) {
            this.category_id = category_id;
        }

        public void setChief_image(String chief_image) {
            this.chief_image = chief_image;
        }

        public void setCreated_time(int created_time) {
            this.created_time = created_time;
        }

        public void setCreator_id(int creator_id) {
            this.creator_id = creator_id;
        }

        public void setEntity_hash(String entity_hash) {
            this.entity_hash = entity_hash;
        }

        public void setEntity_id(int entity_id) {
            this.entity_id = entity_id;
        }

        public void setIntro(String intro) {
            this.intro = intro;
        }

        public void setDetail_images(List<String> detail_images) {
            this.detail_images = detail_images;
        }

        public void setItem_id_list(List<String> item_id_list) {
            this.item_id_list = item_id_list;
        }

        public void setItem_list(List<ItemListEntity> item_list) {
            this.item_list = item_list;
        }

        public String getBrand() {
            return brand;
        }

        public String getCategory_id() {
            return category_id;
        }

        public String getChief_image() {
            return chief_image;
        }

        public int getCreated_time() {
            return created_time;
        }

        public int getCreator_id() {
            return creator_id;
        }

        public String getEntity_hash() {
            return entity_hash;
        }

        public int getEntity_id() {
            return entity_id;
        }

        public String getIntro() {
            return intro;
        }

        public List<String> getDetail_images() {
            return detail_images;
        }

        public List<String> getItem_id_list() {
            return item_id_list;
        }

        public List<ItemListEntity> getItem_list() {
            return item_list;
        }

        public ArrayList<Object> getPoker_id_list() {
            return poker_id_list;
        }

        public void setPoker_id_list(ArrayList<Object> poker_id_list) {
            this.poker_id_list = poker_id_list;
        }

        public static class ItemListEntity {
            /**
             * buy_link : http://api.guoku.com/mobile/v4/item/521838447655/?type=mobile
             * cid : 50002766
             * default : True
             * entity_id : 1546740
             * foreign_price : 0.00
             * id : 321219
             * origin_id : 521838447655
             * origin_source : taobao.com
             * price : 19
             * rank : 0
             * status : 2
             * volume : 0
             */

            private String buy_link;
            private String cid;
            @JSONField(name = "default")
            private String defaultX;
            private String entity_id;
            private String foreign_price;
            private String id;
            private String origin_id;
            private String origin_source;
            private int price;
            private String rank;
            private String status;
            private String volume;

            public void setBuy_link(String buy_link) {
                this.buy_link = buy_link;
            }

            public void setCid(String cid) {
                this.cid = cid;
            }

            public void setDefaultX(String defaultX) {
                this.defaultX = defaultX;
            }

            public void setEntity_id(String entity_id) {
                this.entity_id = entity_id;
            }

            public void setForeign_price(String foreign_price) {
                this.foreign_price = foreign_price;
            }

            public void setId(String id) {
                this.id = id;
            }

            public void setOrigin_id(String origin_id) {
                this.origin_id = origin_id;
            }

            public void setOrigin_source(String origin_source) {
                this.origin_source = origin_source;
            }

            public void setPrice(int price) {
                this.price = price;
            }

            public void setRank(String rank) {
                this.rank = rank;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public void setVolume(String volume) {
                this.volume = volume;
            }

            public String getBuy_link() {
                return buy_link;
            }

            public String getCid() {
                return cid;
            }

            public String getDefaultX() {
                return defaultX;
            }

            public String getEntity_id() {
                return entity_id;
            }

            public String getForeign_price() {
                return foreign_price;
            }

            public String getId() {
                return id;
            }

            public String getOrigin_id() {
                return origin_id;
            }

            public String getOrigin_source() {
                return origin_source;
            }

            public int getPrice() {
                return price;
            }

            public String getRank() {
                return rank;
            }

            public String getStatus() {
                return status;
            }

            public String getVolume() {
                return volume;
            }
        }
    }


    public static class NoteEntity {
        /**
         * creator : {"avatar_large":"http://imgcdn.guoku.com/avatar/large_14_5024e58479be25f0e253035cd77cbf3d.png","avatar_small":"http://imgcdn.guoku.com/avatar/large_14_5024e58479be25f0e253035cd77cbf3d.png","bio":"果库谁人不识货","city":"卢森堡","email":"aki.liao@gmail.com","entity_note_count":3218,"fan_count":951,"following_count":19,"gender":"F","is_active":"2","is_censor":false,"like_count":736,"location":"海外","nickname":"亚飞","relation":0,"tag_count":212,"user_id":14,"website":""}
         */



        /**
         * comment_count : 0
         * content : 淡淡的盐味带着浓浓瓜子仁的油香气，怎么吃都不口干，越吃越香。
         * created_time : 1441886619
         * entity_id : 1546740
         * is_selected : 1
         * note_id : 744903
         * poke_already : 0
         * poke_count : 0
         * post_time : 2015-09-10 20:03:39
         * updated_time : 1441886619
         * user_id : 14
         */
        private CreatorEntity creator;
        private int comment_count;
        private String content;
        private int created_time;
        private String entity_id;
        private int is_selected;
        private int note_id;
        private int poke_already;
        private int poke_count;
        private String post_time;
        private int updated_time;
        private String user_id;

        public void setCreator(CreatorEntity creator) {
            this.creator = creator;
        }

        public CreatorEntity getCreator() {
            return creator;
        }

        public void setComment_count(int comment_count) {
            this.comment_count = comment_count;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public void setCreated_time(int created_time) {
            this.created_time = created_time;
        }

        public void setEntity_id(String entity_id) {
            this.entity_id = entity_id;
        }

        public void setIs_selected(int is_selected) {
            this.is_selected = is_selected;
        }

        public void setNote_id(int note_id) {
            this.note_id = note_id;
        }

        public void setPoke_already(int poke_already) {
            this.poke_already = poke_already;
        }

        public void setPoke_count(int poke_count) {
            this.poke_count = poke_count;
        }

        public void setPost_time(String post_time) {
            this.post_time = post_time;
        }

        public void setUpdated_time(int updated_time) {
            this.updated_time = updated_time;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public int getComment_count() {
            return comment_count;
        }

        public String getContent() {
            return content;
        }

        public int getCreated_time() {
            return created_time;
        }

        public String getEntity_id() {
            return entity_id;
        }

        public int getIs_selected() {
            return is_selected;
        }

        public int getNote_id() {
            return note_id;
        }

        public int getPoke_already() {
            return poke_already;
        }

        public int getPoke_count() {
            return poke_count;
        }

        public String getPost_time() {
            return post_time;
        }

        public int getUpdated_time() {
            return updated_time;
        }

        public String getUser_id() {
            return user_id;
        }

        public static class CreatorEntity {
            /**
             * avatar_large : http://imgcdn.guoku.com/avatar/large_14_5024e58479be25f0e253035cd77cbf3d.png
             * avatar_small : http://imgcdn.guoku.com/avatar/large_14_5024e58479be25f0e253035cd77cbf3d.png
             * bio : 果库谁人不识货
             * city : 卢森堡
             * email : aki.liao@gmail.com
             * entity_note_count : 3218
             * fan_count : 951
             * following_count : 19
             * gender : F
             * is_active : 2
             * is_censor : false
             * like_count : 736
             * location : 海外
             * nickname : 亚飞
             * relation : 0
             * tag_count : 212
             * user_id : 14
             * website :
             */

            private String avatar_large;
            private String avatar_small;
            private String bio;
            private String city;
            private String email;
            private int entity_note_count;
            private int fan_count;
            private int following_count;
            private String gender;
            private String is_active;
            private boolean is_censor;
            private int like_count;
            private String location;
            private String nickname;
            private int relation;
            private int tag_count;
            private int user_id;
            private String website;

            public void setAvatar_large(String avatar_large) {
                this.avatar_large = avatar_large;
            }

            public void setAvatar_small(String avatar_small) {
                this.avatar_small = avatar_small;
            }

            public void setBio(String bio) {
                this.bio = bio;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public void setEmail(String email) {
                this.email = email;
            }

            public void setEntity_note_count(int entity_note_count) {
                this.entity_note_count = entity_note_count;
            }

            public void setFan_count(int fan_count) {
                this.fan_count = fan_count;
            }

            public void setFollowing_count(int following_count) {
                this.following_count = following_count;
            }

            public void setGender(String gender) {
                this.gender = gender;
            }

            public void setIs_active(String is_active) {
                this.is_active = is_active;
            }

            public void setIs_censor(boolean is_censor) {
                this.is_censor = is_censor;
            }

            public void setLike_count(int like_count) {
                this.like_count = like_count;
            }

            public void setLocation(String location) {
                this.location = location;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public void setRelation(int relation) {
                this.relation = relation;
            }

            public void setTag_count(int tag_count) {
                this.tag_count = tag_count;
            }

            public void setUser_id(int user_id) {
                this.user_id = user_id;
            }

            public void setWebsite(String website) {
                this.website = website;
            }

            public String getAvatar_large() {
                return avatar_large;
            }

            public String getAvatar_small() {
                return avatar_small;
            }

            public String getBio() {
                return bio;
            }

            public String getCity() {
                return city;
            }

            public String getEmail() {
                return email;
            }

            public int getEntity_note_count() {
                return entity_note_count;
            }

            public int getFan_count() {
                return fan_count;
            }

            public int getFollowing_count() {
                return following_count;
            }

            public String getGender() {
                return gender;
            }

            public String getIs_active() {
                return is_active;
            }

            public boolean getIs_censor() {
                return is_censor;
            }

            public int getLike_count() {
                return like_count;
            }

            public String getLocation() {
                return location;
            }

            public String getNickname() {
                return nickname;
            }

            public int getRelation() {
                return relation;
            }

            public int getTag_count() {
                return tag_count;
            }

            public int getUser_id() {
                return user_id;
            }

            public String getWebsite() {
                return website;
            }
        }
    }
}
