package com.ekwing.students.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.ekwing.students.config.Logger;
import com.ekwing.students.entity.ConSentenceBean;
import com.ekwing.students.entity.ConfirmResultBean;
import com.ekwing.students.entity.ExpDetailBean;
import com.ekwing.students.entity.HHWBean;
import com.ekwing.students.entity.HHWDetailsBean;
import com.ekwing.students.entity.HWIndexBean;
import com.ekwing.students.entity.LevelBean;
import com.ekwing.students.entity.ListenBean;
import com.ekwing.students.entity.ListenContentsBean;
import com.ekwing.students.entity.ListenQueryBean;
import com.ekwing.students.entity.ListenQuestionBean;
import com.ekwing.students.entity.ListenResultBean;
import com.ekwing.students.entity.ListenWriteQueryBean;
import com.ekwing.students.entity.MineBean;
import com.ekwing.students.entity.NewTaskBean;
import com.ekwing.students.entity.OptionBean;
import com.ekwing.students.entity.PetsBean;
import com.ekwing.students.entity.RankBean;
import com.ekwing.students.entity.ReadEkwingBean;
import com.ekwing.students.entity.ReadUnderstandBean;
import com.ekwing.students.entity.RecordListBean;
import com.ekwing.students.entity.ResultBean;
import com.ekwing.students.entity.RetListBean;
import com.ekwing.students.entity.SBCDetailsBean;
import com.ekwing.students.entity.SBCWordBean;
import com.ekwing.students.entity.ScoreDetailsBean;
import com.ekwing.students.entity.SelectBean;
import com.ekwing.students.entity.SoundListBean;
import com.ekwing.students.entity.StaticsBean;
import com.ekwing.students.entity.UserInfoBean;
import com.ekwing.students.entity.WordConfirmBean;
import com.ekwing.students.entity.WordWriteBean;
import com.ekwing.students.entity.WordsBean;

public class JSonParcelUtils {

	private static JSONArray details;
	private static Random random = new Random();

	/**
	 * 解析SBC返回的结果数据
	 * 
	 * @param applicationContext
	 * @param json
	 */
	public static SBCWordBean getSBCResult(Context applicationContext, String json, String id, String url, String text) {
		SBCWordBean bean = new SBCWordBean(url, text, id);
		int score = 0;
		ArrayList<String> er = new ArrayList<String>();
		ArrayList<String> errStatic = new ArrayList<String>();
		try {
			JSONObject root = new JSONObject(json);
			if (root.has("result")) {
				JSONObject ret = root.getJSONObject("result");
				if (ret.has("pron")) {
					bean.setPron(ret.getString("pron"));
				}
				if (ret.has("overall")) {
					bean.setOverall(ret.getString("overall"));
				}
				if (ret.has("accuracy")) {
					bean.setAccuracy(ret.getString("accuracy"));
				}
				if (ret.has("fluency")) {
					JSONObject fluency = ret.getJSONObject("fluency");
					if (fluency.has("overall")) {
						bean.setFluencyoverall(fluency.getString("overall"));
					}
					if (fluency.has("pause")) {
						bean.setFluencypause(fluency.getString("pause"));
					}
					if (fluency.has("speed")) {
						bean.setFluencyspeed(fluency.getString("speed"));
					}
				}
				if (ret.has("rhythm")) {
					JSONObject rhythm = ret.getJSONObject("rhythm");
					if (rhythm.has("overall")) {
						bean.setRhythmoverall(rhythm.getString("overall"));
					}
					if (rhythm.has("sense")) {
						bean.setRhythmsense(rhythm.getString("sense"));
					}
					if (rhythm.has("stress")) {
						bean.setRhythmstress(rhythm.getString("stress"));
					}
					if (rhythm.has("tone")) {
						bean.setRhythmtone(rhythm.getString("tone"));
					}
				}
				if (ret.has("details")) {
					details = ret.getJSONArray("details");
					List<SBCDetailsBean> listd = new ArrayList<SBCDetailsBean>();
					SBCDetailsBean detailsBean;
					JSONObject item;
					for (int i = 0; i < details.length(); i++) {
						item = details.getJSONObject(i);
						detailsBean = new SBCDetailsBean();
						if (item.has("char")) {
							detailsBean.setChars(item.getString("char"));
						}
						if (item.has("score")) {
							int sc = Integer.parseInt(item.getString("score"));
							detailsBean.setScore(sc + "");
							if (sc < 60) {
								er.add(item.getString("char"));
							}
							score = score + Integer.parseInt(item.getString("score"));
						}
						if (item.has("start")) {
							detailsBean.setStart(item.getString("start"));
						}
						if (item.has("end")) {
							detailsBean.setEnd(item.getString("end"));
						}
						if (item.has("dur")) {
							detailsBean.setDur(item.getString("dur"));
						}
						if (item.has("fluency")) {
							detailsBean.setFluency(item.getString("fluency"));
						}
						if (item.has("stressref")) {
							detailsBean.setStressref(item.getString("stressref"));
						}
						if (item.has("stressscore")) {
							detailsBean.setStressscore(item.getString("stressscore"));
						}
						if (item.has("toneref")) {
							detailsBean.setToneref(item.getString("toneref"));
						}
						if (item.has("tonescore")) {
							detailsBean.setTonescore(item.getString("tonescore"));
						}
						if (item.has("senseref")) {
							detailsBean.setSenseref(item.getString("senseref"));
						}
						if (item.has("sensescore")) {
							detailsBean.setSensescore(item.getString("sensescore"));
						}
						listd.add(detailsBean);
					}
					bean.setAverageScore((int) score / details.length() + "");
					bean.setErrChars(er);
					bean.setDetails(listd);
				}

				if (ret.has("statics")) {
					JSONArray statics = ret.getJSONArray("statics");
					List<StaticsBean> lists = new ArrayList<StaticsBean>();
					StaticsBean staticsBean;
					JSONObject items;
					StringBuilder sb = new StringBuilder();
					for (int i = 0; i < statics.length(); i++) {
						items = statics.getJSONObject(i);
						staticsBean = new StaticsBean();
						if (items.has("char")) {
							staticsBean.setChars(items.getString("char"));
							sb.append(CharUtil.getChar(items.getString("char")));
						}
						if (items.has("count")) {
							staticsBean.setCount(items.getString("count"));
						}
						if (items.has("score")) {
							int scr = Integer.parseInt(items.getString("score"));
							staticsBean.setScore(scr + "");
							if (scr < 60) {
								errStatic.add(CharUtil.getChar(items.getString("char")));
							}
							staticsBean.setScore(items.getString("score"));
						}
						lists.add(staticsBean);
					}
					bean.setStaticsSum(sb + "");
					bean.setStatics(lists);
					bean.setErrStaticChars(errStatic);
				}

			}
			return bean;
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}

	/*
	 * 获取作业列表的数据
	 */
	public static List<HWIndexBean> getHwByDay(Context context, String json) {
		return JSON.parseArray(json.toString(), HWIndexBean.class);
	}

	/**
	 * 返回我的系列数据
	 * 
	 * @param context
	 * @param myjson
	 */
	public static List<MineBean> getMyStudyLevel(Context context, String myjson) {
		List<MineBean> lists = new ArrayList<MineBean>();
		lists = JSON.parseArray(myjson.toString(), MineBean.class);
		return lists;

	}

	public static List<MineBean> getMyStudyLevel1(Context context, String myjson, String type) {
		List<MineBean> lists = null;
		Logger.e("getMyStudyLevel1", "getMyStudyLevel1------------------------>" + type);
		Logger.e("getMyStudyLevel1", "getMyStudyLevel1------------------------>" + myjson);
		try {
			lists = new ArrayList<MineBean>();
			MineBean bean = null;
			JSONArray dataArray = new JSONArray(myjson);
			JSONObject item = null;
			for (int i = 0; i < dataArray.length(); i++) {
				item = dataArray.getJSONObject(i);
				bean = new MineBean();
				if (item.has("mid")) {
					Logger.e("bean", "bean----------------------------->" + item.getString("mid"));
					bean.setMid(type + item.getString("mid"));
				}
				if (item.has("times")) {
					Logger.e("bean", "bean----------------times------------->" + item.getString("times"));
					bean.setTimes(item.getString("times"));
				}
				if (item.has("context")) {
					Logger.e("bean", "bean-------------context---------------->" + item.getString("context"));
					bean.setContext(item.getString("context"));
				}
				if (item.has("impotContext")) {
					Logger.e("bean", "bean----------------impotContext------------->" + item.getString("impotContext"));
					bean.setSrtImpotContext(item.getString("impotContext"));
				}
				if (item.has("others")) {
					Logger.e("bean", "bean------------others----------------->" + item.getString("others"));
					bean.setOthers(item.getString("others"));
				}
				if (item.has("imgs")) {
					Logger.e("bean", "bean----------imgs------------------->" + item.getString("imgs"));
					bean.setSrtImgs(item.getString("imgs"));
				}
				bean.setType(type);
				lists.add(bean);
			}
			return lists;

		} catch (JSONException e) {
			Logger.e("JSONException", "JSONException-------------->" + e.toString());
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取未完成的作题
	 * 
	 * @param isCompleteHWActivity
	 * @param result
	 */
	public static List<WordsBean> getNotComplete(Context context, String result) {
		return JSON.parseArray(result.toString(), WordsBean.class);
	}

	/**
	 * 获取未完成的作题
	 * 
	 * @param isCompleteHWActivity
	 * @param result
	 */
	public static ListenQueryBean getNotCompleteButCommit(Context context, String result) {
		ListenQueryBean queryBean = new ListenQueryBean();
		List<SoundListBean> soundList = new ArrayList<SoundListBean>();
		List<RetListBean> retList = new ArrayList<RetListBean>();
		List<WordsBean> beanLists = JSON.parseArray(result.toString(), WordsBean.class);
		SoundListBean soundBean;
		RetListBean retBean;
		int size = beanLists.size();
		for (int i = 0; i < size; i++) {
			retBean = new RetListBean();
			retBean.setId(beanLists.get(i).getId());
			retBean.setRetext(beanLists.get(i).getRetext());
			retList.add(retBean);

			soundBean = new SoundListBean();
			soundBean.setUrl(beanLists.get(i).getSoundPath());
			soundList.add(soundBean);
		}
		queryBean.setSoundList(soundList);
		queryBean.setRetList(retList);

		return queryBean;
	}

	/**
	 * 获取完成作业的信息
	 * 
	 * @param applicationContext
	 * @param result
	 * @return
	 */
	public static ListenQueryBean getQueryComplete(Context context, String result) {
		return JSON.parseObject(result, ListenQueryBean.class);
	}

	/**
	 * 得到句子闯关的SBC结果
	 * 
	 * @param context
	 * @param json
	 */
	public static SBCWordBean getArticleConfirm(Context context, String json, String id, String url, String text) {
		SBCWordBean bean = new SBCWordBean(url, text, id);
		String averageScore = null;
		int sum = 0;
		try {
			JSONObject root = new JSONObject(json);
			if (root.has("result")) {
				String result = root.getString("result");
				bean = JSON.parseObject(result, SBCWordBean.class);

			}

			int size = bean.getDetails().size();
			if (size > 0) {

				for (int i = 0; i < size; i++) {
					sum = sum + Integer.parseInt(bean.getDetails().get(i).getScore());
				}
				averageScore = (sum / size) * 25 + "";
				bean.setRet(json);
				bean.setAverageScore(averageScore);
			}
			return bean;

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bean;
	}

	public static UserInfoBean getUserInfos(Context context, String result) {
		UserInfoBean bean = new UserInfoBean();
		bean = JSON.parseObject(result, UserInfoBean.class);
		return bean;
	}

	/**
	 * 获取最新任务的接口
	 * 
	 * @param context
	 * @param result
	 */
	public static NewTaskBean getNewTask(Context context, String result) {
		List<NewTaskBean> lists = new ArrayList<NewTaskBean>();
		NewTaskBean bean = new NewTaskBean();
		try {
			lists = JSON.parseArray(result, NewTaskBean.class);
			bean = lists.get(0);
			return bean;
		} catch (Exception e) {
			bean = new NewTaskBean();
			return bean;
		}

	}

	/**
	 * 得到话题列表的数据
	 * 
	 * @param context
	 * @param result
	 */
	public static List<LevelBean> getAddListData(Context context, String result) {
		List<LevelBean> lists = new ArrayList<LevelBean>();
		try {
			if (result != null && !"".equals(result)) {
				lists = JSON.parseArray(result.toString(), LevelBean.class);
			}
		} catch (Exception e) {
			lists = new ArrayList<LevelBean>();
		}
		return lists;
	}

	/**
	 * 得到排行数据列表
	 * 
	 * @param rankingActivity
	 * @param result
	 */
	public static RankBean getRankData(Context context, String result) {
		ArrayList<RankBean> lists = new ArrayList<RankBean>();
		RankBean bean = new RankBean();
		try {
			lists = (ArrayList<RankBean>) JSON.parseArray(result, RankBean.class);
			bean = lists.get(0);
			return bean;
		} catch (Exception e) {
			bean = new RankBean();
			return bean;
		}
	}

	/**
	 * 返回提交成功后的结果数据
	 * 
	 * @param context
	 * @param result
	 * @return
	 */
	public static ConfirmResultBean getCommitRestlt(Context context, String result) {
		ConfirmResultBean bean = new ConfirmResultBean();
		List<ConfirmResultBean> list = new ArrayList<ConfirmResultBean>();
		try {
			list = JSON.parseArray(result, ConfirmResultBean.class);
			bean = list.get(0);
			return bean;
		} catch (Exception e) {
			bean = new ConfirmResultBean();
			return bean;
		}

	}

	public static String getRestluSmart(String json, String id, String url, String text, String score) {
		try {
			JSONObject root = new JSONObject(json);
			String result = root.getString("result");
			result = "{\"id\":\"" + id + "\"," + "\"text\":\"" + TextUtils.replaceBlank(text) + "\"," + "\"url\":\"" + url + "\"," + "\"speed\":\""
					+ 1 + "\"," + "\"score\":\"" + score + "\"," + result.substring(1, result.length());
			return result;
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return "";
	}

	public static String getRestlu(String json, String id, String url, String text) {
		try {
			JSONObject root = new JSONObject(json);
			String result = root.getString("result");
			result = "{\"id\":\"" + id + "\"," + "\"text\":\"" + TextUtils.replaceBlank(text) + "\"," + "\"url\":\"" + url + "\","
					+ result.substring(1, result.length());
			return result;
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return "";
	}

	public static String getRestlu2(String id, String url, String text, List<OptionBean> choo, String select) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < choo.size(); i++) {
			sb.append("," + choo.get(i).getKey() + ":" + choo.get(i).getText());
		}
		String sbr = sb.substring(1);
		String result = "";
		result = "{\"id\":\"" + id + "\"," + "\"text\":\"" + TextUtils.replaceBlank(text) + "\"," + "\"url\":\"" + url + "\"," + "\"chooses\":"
				+ "\"{" + sbr + "}" + "\"," + "\"select\":\"" + select + "\"}";
		return result;

	}

	public static String getRestlu1(String json, String id, String url, String text, String score) {
		try {
			JSONObject root = new JSONObject(json);
			String result = root.getString("result");
			result = "{\"id\":\"" + id + "\"," + "\"text\":\"" + TextUtils.replaceBlank(text) + "\"," + "\"url\":\"" + url + "\"," + "\"score\":\""
					+ score + "\"," + result.substring(1, result.length());
			return result;
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return "";
	}

	public static List<WordConfirmBean> getWordsConfirmData(Context context, String result) {
		List<WordConfirmBean> list = new ArrayList<WordConfirmBean>();
		try {
			Logger.i("getWordsConfirmData", "getWordsConfirmData------------------->" + result);
			list = JSON.parseArray(result.toString(), WordConfirmBean.class);
			Logger.i("getWordsConfirmData", "getWordsConfirmData------------------->" + list.size());
			return list;
		} catch (Exception e) {
			list = new ArrayList<WordConfirmBean>();
			Logger.i("getWordsConfirmData", "Exception------------------->" + e.toString());
			// TODO: handle exception
		}
		return list;
	}

	/**
	 * 得到宠物信息
	 * 
	 * @param result
	 */
	public static List<PetsBean> getPetData(String result) {
		List<PetsBean> lists = new ArrayList<PetsBean>();
		try {
			lists = JSON.parseArray(result, PetsBean.class);
		} catch (Exception e) {
			lists = new ArrayList<PetsBean>();
		}
		return lists;
	}

	/**
	 * 得到单词听写的题目
	 * 
	 * @param context
	 * @param result
	 */
	public static ArrayList<WordWriteBean> getWordsListenWriteBasicData(Context context, String result) {
		ArrayList<WordWriteBean> lists = new ArrayList<WordWriteBean>();
		try {
			lists = (ArrayList<WordWriteBean>) JSON.parseArray(result, WordWriteBean.class);
		} catch (Exception e) {
			lists = new ArrayList<WordWriteBean>();
		}
		return lists;
	}

	// /**
	// * 得到单词听写的结果bean
	// *
	// * @param context
	// * @param result
	// */
	// public static WordsListenWriteResultBean getListenWriteResultData(Context
	// context, String result) {
	//
	// List<WordsListenWriteResultBean> lists = new
	// ArrayList<WordsListenWriteResultBean>();
	// WordsListenWriteResultBean bean = new WordsListenWriteResultBean();
	// try {
	// lists = JSON.parseArray(result, WordsListenWriteResultBean.class);
	// } catch (Exception e) {
	// lists = new ArrayList<WordsListenWriteResultBean>();
	// }
	// if (lists != null && lists.size() > 0) {
	// bean = lists.get(0);
	// }
	// return bean;
	//
	// }

	/**
	 * 提交作业返回的数据
	 * 
	 * @param context
	 * @param result
	 * @return
	 * @return
	 */
	public static ScoreDetailsBean getCommitsResult(Context context, String result) {
		ScoreDetailsBean bean = new ScoreDetailsBean();
		ArrayList<ResultBean> lists = new ArrayList<ResultBean>();
		try {
			lists = (ArrayList<ResultBean>) JSON.parseArray(result, ResultBean.class);
			if (lists != null && lists.size() > 0) {
				bean = lists.get(0).getScoreDetail();
			}
		} catch (Exception e) {
			return bean;
		}
		return bean;
	}

	public static ResultBean getCommitsResultBean(Context context, String result) {
		ResultBean bean = new ResultBean();
		ArrayList<ResultBean> lists = new ArrayList<ResultBean>();
		try {
			lists = (ArrayList<ResultBean>) JSON.parseArray(result, ResultBean.class);
			if (lists != null && lists.size() > 0) {
				bean = lists.get(0);
			}
		} catch (Exception e) {
			return bean;
		}
		return bean;
	}

	/**
	 * 解析单词听写返回的做题情况
	 * 
	 * @param context
	 * @param result
	 */
	public static ArrayList<ListenWriteQueryBean> getWordsListenWriteParcelData(Context context, String result) {
		ArrayList<ListenWriteQueryBean> lists = new ArrayList<ListenWriteQueryBean>();
		try {
			lists = (ArrayList<ListenWriteQueryBean>) JSON.parseArray(result, ListenWriteQueryBean.class);
		} catch (Exception e) {
			lists = new ArrayList<ListenWriteQueryBean>();
		}

		return lists;

	}

	/**
	 * 得到阅读理解的bean
	 * 
	 * @param context
	 * @param result
	 */
	public static List<ReadUnderstandBean> getReadUnderstandData(Context context, String result) {
		List<ReadUnderstandBean> lists = new ArrayList<ReadUnderstandBean>();
		try {
			lists = JSON.parseArray(result.toString(), ReadUnderstandBean.class);
		} catch (Exception e) {
			lists = new ArrayList<ReadUnderstandBean>();
		}
		return lists;
	}

	public static List<ReadEkwingBean> getSmartData(Context context, String result) {
		List<ReadEkwingBean> lists = new ArrayList<ReadEkwingBean>();
		try {
			lists = JSON.parseArray(result, ReadEkwingBean.class);
		} catch (Exception e) {
		}
		return lists;
	}

	/**
	 * 得到经验值相关信息
	 * 
	 * @param applicationContext
	 * @param result
	 */
	public static ExpDetailBean getExpDetail(Context context, String result) {
		ExpDetailBean bean = new ExpDetailBean();
		ArrayList<ResultBean> lists = new ArrayList<ResultBean>();
		try {
			lists = (ArrayList<ResultBean>) JSON.parseArray(result, ResultBean.class);
			if (lists != null && lists.size() > 0) {
				bean = lists.get(0).getExpDetail();
			}
		} catch (Exception e) {
			return bean;
		}
		return bean;
	}

	/**
	 * 解析选择列表的数据
	 * 
	 * @param selectMapAndThemeActivity
	 * @param result
	 */
	public static ArrayList<SelectBean> parseSelect(Context context, String result) {
		ArrayList<SelectBean> lists = new ArrayList<SelectBean>();
		try {
			lists = (ArrayList<SelectBean>) JSON.parseArray(result, SelectBean.class);
		} catch (Exception e) {
			lists = new ArrayList<SelectBean>();
		}
		return lists;

	}

	/**
	 * 一个话题、地图、教材的数据解析
	 * 
	 * @param themePartActivity
	 * @param result
	 */
	public static ArrayList<LevelBean> getSelectData(Context context, String result) {
		ArrayList<LevelBean> lists = new ArrayList<LevelBean>();
		try {
			lists = (ArrayList<LevelBean>) JSON.parseArray(result, LevelBean.class);
		} catch (Exception e) {
			lists = new ArrayList<LevelBean>();
		}

		return lists;
	}

	/**
	 * 得到
	 * 
	 * @param context
	 * @param result
	 */
	public static ResultBean getSentenseContent(Context context, String result) {
		ResultBean bean = new ResultBean();
		try {
			bean = JSON.parseObject(result, ResultBean.class);
		} catch (Exception e) {
			return bean;
		}
		return bean;
	}

	/**
	 * 得到听力理解结果的数据
	 * 
	 * @param context
	 * @param result
	 */
	public static ListenResultBean getListenResult(Context context, String result) {

		ListenResultBean bean = new ListenResultBean();
		List<ListenResultBean> lists = null;
		try {
			lists = JSON.parseArray(result, ListenResultBean.class);
		} catch (Exception e) {
			return bean;
		}
		if (lists != null && lists.size() > 0) {
			bean = lists.get(0);
		}
		return bean;
	}

	/**
	 * 得到游客登录本地json数据
	 * 
	 * @param homeWorkMainActivity
	 */
	public static ArrayList<HWIndexBean> getTravelLocalHomework(Context context) {
		ArrayList<HWIndexBean> lists = new ArrayList<HWIndexBean>();
		String json = "";
		try {
			json = FileUtils.readTxtFile(context.getAssets().open("homework.json"));
			Logger.e("", "home_json==============>" + json);
			JSONObject root = new JSONObject(json);
			if (root.has("data")) {
				String result = root.getString("data");
				lists = (ArrayList<HWIndexBean>) JSON.parseArray(result.toString(), HWIndexBean.class);
			}
		} catch (Exception e) {
			e.printStackTrace();
			lists = new ArrayList<HWIndexBean>();
		}
		return lists;
	}

	/**
	 * 得到单词听写的作业
	 * 
	 * @param context
	 */
	public static ArrayList<WordWriteBean> getTravelWordsListenWrite(Context context) {
		ArrayList<WordWriteBean> lists = new ArrayList<WordWriteBean>();
		String json = "";
		try {
			json = FileUtils.readTxtFile(context.getAssets().open("listenwrite.json"));
			Logger.e("", "得到单词听写的作业-----------json------>" + json);
			JSONObject root = new JSONObject(json);
			if (root.has("data")) {
				String result = root.getString("data");
				lists = (ArrayList<WordWriteBean>) JSON.parseArray(result, WordWriteBean.class);
			}
		} catch (Exception e) {
			Logger.e("", "得到单词听写的作业--------------------->" + e.toString());
			lists = new ArrayList<WordWriteBean>();
		}
		Logger.e("", "得到单词听写的作业--------------------->" + JSON.toJSONString(lists));
		return lists;
	}

	/**
	 * 游客登录获取阅读理解做题作业
	 * 
	 * @param context
	 * @return
	 */
	public static ArrayList<ReadUnderstandBean> getTravelReadUndertand(Context context) {
		ArrayList<ReadUnderstandBean> lists = new ArrayList<ReadUnderstandBean>();
		String json = "";
		try {
			json = FileUtils.readTxtFile(context.getAssets().open("readunderstand.json"));
			Logger.e("", "得到单词听写的作业-----------json------>" + json);
			JSONObject root = new JSONObject(json);
			if (root.has("data")) {
				String result = root.getString("data");
				lists = (ArrayList<ReadUnderstandBean>) JSON.parseArray(result, ReadUnderstandBean.class);
			}
		} catch (Exception e) {
			lists = new ArrayList<ReadUnderstandBean>();
		}
		return lists;
	}

	/**
	 * 获取游客登录中单词听读作业或课文听读作业
	 * 
	 * @param context
	 * @return
	 */
	public static ArrayList<WordsBean> getTravelWordsArticle(String category, Context context) {
		ArrayList<WordsBean> lists = new ArrayList<WordsBean>();
		String json = "";
		try {
			if ("1".equals(category)) {
				json = FileUtils.readTxtFile(context.getAssets().open("word.json"));
			} else if ("2".equals(category)) {
				json = FileUtils.readTxtFile(context.getAssets().open("article.json"));
			}
			Logger.e("", "得到单词听写的作业-----------json------>" + json);
			JSONObject root = new JSONObject(json);
			if (root.has("data")) {
				String result = root.getString("data");
				lists = (ArrayList<WordsBean>) JSON.parseArray(result, WordsBean.class);
			}
		} catch (Exception e) {
			lists = new ArrayList<WordsBean>();
		}
		return lists;
	}

	/**
	 * 游客登录准备解析数据
	 * 
	 * @param context
	 * @param lists
	 * @return
	 */
	public static ListenQueryBean getWordsAndArticle(Context context, ArrayList<WordsBean> lists) {
		ListenQueryBean queryBean = new ListenQueryBean();
		ArrayList<SoundListBean> soundList = new ArrayList<SoundListBean>();
		ArrayList<RecordListBean> recordList = new ArrayList<RecordListBean>();
		ArrayList<RetListBean> retList = new ArrayList<RetListBean>();
		int size = lists.size();
		SoundListBean soundBean = null;
		RecordListBean recordBean = null;
		RetListBean retBean = null;
		if (lists != null && lists.size() > 0) {
			for (int i = 0; i < size; i++) {

				if (lists.get(i).getSoundPath() != null && !"".equals(lists.get(i).getSoundPath())) {
					soundBean = new SoundListBean();
					soundBean.setStart(lists.get(i).getStart() + "");
					soundBean.setKeep(lists.get(i).getDur() + "");
					soundBean.setUrl(lists.get(i).getSoundPath());
					soundList.add(soundBean);
				}
				if (lists.get(i).getReSoundPath() != null && !"".equals(lists.get(i).getReSoundPath())) {
					recordBean = new RecordListBean();
					recordBean.setId(lists.get(i).getId());
					// recordBean.setStart(0);
					// recordBean.setKeep(Integer.parseInt(sentenceLists.get(i).getRecord_duration()));
					recordBean.setTid(lists.get(i).getId());
					recordBean.setUrl(lists.get(i).getReSoundPath());
					recordList.add(recordBean);

					retBean = new RetListBean();
					retBean.setId(lists.get(i).getId());
					retBean.setRetext(lists.get(i).getRetext());
					retList.add(retBean);
				}
			}
		}
		Logger.d("游客", "文本------>" + JSON.toJSONString(retList));
		Logger.d("游客", "录音----->" + JSON.toJSONString(recordList));
		Logger.d("游客", "援引----->" + JSON.toJSONString(soundList));

		queryBean.setRecordList(recordList);
		queryBean.setRetList(retList);
		queryBean.setSoundList(soundList);
		return queryBean;
	}

	/**
	 * 游客得到做题结果数据（单词听读和课文听读）
	 * 
	 * @param context
	 * @param articleLists
	 */
	public static ConfirmResultBean getTravelScorBean(Context context, ArrayList<WordsBean> lists) {
		ConfirmResultBean bean = new ConfirmResultBean();
		try {
			if (lists != null && !"".equals(lists) && lists.size() > 0) {
				int size = lists.size();
				int fluency = 0; // 流利度
				int integrity = 0; // 完整度
				int accuracy = 0; // 准确度
				int count = 0; // 发音60以上的个数
				for (int i = 0; i < size; i++) {
					SBCWordBean sbcResult = lists.get(i).getSBCResult();
					fluency = fluency + Integer.parseInt(sbcResult.getFluencyoverall());
					integrity = integrity + Integer.parseInt(sbcResult.getIntegrity());
					accuracy = accuracy + Integer.parseInt(sbcResult.getAccuracy());
					if (Integer.parseInt(sbcResult.getAccuracy()) >= 60) {
						count++;
					}
				}
				fluency = (int) (fluency / size);
				integrity = (int) (integrity / size);
				accuracy = (int) (accuracy / size);
				int score = (int) (count / size);
				bean.setError((size - count) + "");
				bean.setGet1("");
				bean.setGet2("");
				bean.setGrades(score + "");
				bean.setP1(fluency + "");
				bean.setP2(accuracy + "");
				bean.setP3(integrity + "");
				bean.setRight("" + count);
				bean.setTotal("" + size);
				if (score >= 60) {
					int num = random.nextInt(40) + 61;
					bean.setTitle("你的成绩击败了" + num + "%的同学");
				} else {
					bean.setTitle("次成绩不是十分理想，不要气馁，再接再厉~");
				}

			}
		} catch (Exception e) {
			return bean;
		}
		return bean;
	}

	/**
	 * 解析听力理解题
	 * 
	 * @param Context
	 * @param result
	 */
	public static ListenBean getListenHomeWork(Context context, String result) {
		ArrayList<ListenBean> lists = new ArrayList<ListenBean>();
		ListenBean bean = new ListenBean();
		try {
			lists = (ArrayList<ListenBean>) JSON.parseArray(result, ListenBean.class);
			if (lists != null && !"".equals(lists) && lists.size() > 0) {
				bean = lists.get(0);
			}

		} catch (Exception e) {
			Logger.e("exception", "解析听力理解题=====>" + e.toString());
			return bean;
		}
		return bean;
	}

	/**
	 * 得到连词成句的作题内容
	 * 
	 * @param context
	 * @param result
	 * @return
	 */
	public static ConSentenceBean getConnectionData(Context context, String result) {
		ArrayList<ConSentenceBean> lists = new ArrayList<ConSentenceBean>();
		ConSentenceBean bean = new ConSentenceBean();
		try {
			lists = (ArrayList<ConSentenceBean>) JSON.parseArray(result, ConSentenceBean.class);
			if (lists != null && !"".equals(lists) && lists.size() > 0) {
				bean = lists.get(0);
			}
		} catch (Exception e) {
			return bean;
		}
		return bean;
	}

	/**
	 * 得到听力解析的原文
	 * 
	 * @param context
	 * @param result
	 * @return
	 */
	public static ArrayList<ListenContentsBean> getListenUnderQuery(Context context, String result) {
		ArrayList<ListenContentsBean> lists = new ArrayList<ListenContentsBean>();
		try {
			lists = (ArrayList<ListenContentsBean>) JSON.parseArray(result, ListenContentsBean.class);
		} catch (Exception e) {
			// TODO: handle exception
			lists = new ArrayList<ListenContentsBean>();
		}
		return lists;

	}

	
	public static ArrayList<HHWDetailsBean> getHWList(Context context, String result) {
		ArrayList<HHWDetailsBean> lists = new ArrayList<HHWDetailsBean>();
		try {
			lists = (ArrayList<HHWDetailsBean>) JSON.parseArray(result, HHWDetailsBean.class);
		} catch (Exception e) {
			// TODO: handle exception
			lists = new ArrayList<HHWDetailsBean>();
		}
		return lists;
		
	}

}
