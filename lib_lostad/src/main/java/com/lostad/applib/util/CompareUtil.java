package com.lostad.applib.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


/**
 * 根据属性对比列表      排序对象列表List<T> 或  集合列表List<Map<K, V>>    (int String Object 类型的字段  均可进行排序)
 *
 * @param <T>
 * @author Hocean  @version 2016年9月6日 下午4:33:03.
 */
public class CompareUtil<T> {

	public static void main(String args[]) {
		//Demo
		CompareUtilTest.main();
	}

	private static interface IComparator<T> {
		IComparator getComparator();
	}

	/**
	 * 排序 规则
	 *
	 * @author Hocean  @version 2016年9月6日 下午4:33:23.
	 */
	public enum ESort {
		/*缺省*/def(0), /* 正序*/asc(1), /*倒序*/desc(-1);
		int value;

		private ESort(int v) {
			value = v;
		}

		public int get() {
			return value;
		}
	}

	/**
	 * 排序 缺省
	 *
	 * @param list
	 * @param filed
	 */
	public static <T> void sort(List<T> list, String... filed) {
		sort(list, ESort.def, filed);
	}

	/**
	 * 排序 正序
	 *
	 * @param list
	 * @param filed
	 */
	public static <T> void sortAsc(List<T> list, String... filed) {
		sort(list, ESort.asc, filed);
	}

	/**
	 * 排序 倒序
	 *
	 * @param list
	 * @param filed
	 */
	public static <T> void sortDesc(List<T> list, String... filed) {
		sort(list, ESort.desc, filed);
	}

	/**
	 * 排序
	 *
	 * @param list
	 * @param ebuy
	 * @param filed
	 */
	public static <T> void sort(List<T> list, ESort ebuy, String... filed) {
		sort(list, ebuy.get(), filed);
	}

	/**
	 * 排序  ASC = 1; DESC = -1;
	 *
	 * @param list
	 * @param sort
	 * @param filed
	 */
	public static <T> void sort(List<T> list, int sort, String... filed) {
		Collections.sort(list, new ImComparator<T>(sort, filed));
	}

	/**
	 * 复杂排序 推荐
	 *
	 * @param list
	 * @param ebuy
	 * @param filed
	 */
	public static <T> void sort(List<T> list, String[] filed, ESort[] ebuy) {
		int[] sorts = new int[ebuy.length];
		for (int i = 0; i < sorts.length; i++) {
			sorts[i] = ebuy[i].get();
		}
		Collections.sort(list, new ImComparator<T>(sorts, filed));
	}

	/**
	 * 复杂排序
	 *
	 * @param list
	 * @param map
	 */
	public static <T> void sort(List<T> list, Map<String, ESort> map) {
		int len = map.size();
		String[] filed = new String[len];
		ESort[] ebuy = new ESort[len];
		Iterator<Map.Entry<String, ESort>> it = map.entrySet().iterator();
		int count = 0;
		while (it.hasNext() && count < len) {
			Map.Entry<String, ESort> en = it.next();
			filed[count] = en.getKey();
			ebuy[count] = en.getValue();
			count++;
		}
		CompareUtil.sort(list, filed, ebuy);
	}

	/**
	 * 复杂排序
	 *
	 * @param list
	 * @param fs
	 */
	public static <T> void sortFS(List<T> list, String... fs) {
		int len = fs.length / 2;
		ESort[] ebuy = new ESort[len];
		String[] filed = new String[len];
		for (int i = 0; i < len; i++) {
			filed[i] = fs[i * 2];
			ebuy[i] = ESort.valueOf(fs[i * 2 + 1]);
		}
		CompareUtil.sort(list, filed, ebuy);
	}


	private abstract static class AbsComparator<T> implements Comparator<T> {
		public static int SORT_ASC = 1;
		public static int SORT_DESC = -1;

		protected int sort = SORT_ASC; //默认 正序排列
		protected String[] filed = null;
		protected int[] sorts = null;

		public AbsComparator(int sort, String... filed) {
			this.sort = sort;
			this.filed = filed;
		}

		public AbsComparator(int[] sorts, String[] filed) {
			this.sorts = sorts;
			this.filed = filed;
		}
	}

	private static class mapComparator<T> extends AbsComparator<T> {

		public mapComparator(int sort, String... filed) {
			super(sort, filed);

		}

		public mapComparator(int[] sorts, String[] filed) {
			super(sorts, filed);

		}

		@Override
		public int compare(T lhs, T rhs) {
			return 0;
		}
	}


	/**
	 * 实现 排序规则
	 *
	 * @param <T>
	 * @author Hocean  @version 2016年9月6日 下午4:33:03.
	 */
	private static class ImComparator<T> implements Comparator<T> {
		public static int SORT_ASC = 1;
		@SuppressWarnings("unused")
		public static int SORT_DESC = -1;

		private int sort = SORT_ASC; //默认 正序排列

		private String[] filed = null;
		private int[] sorts = null;

		public ImComparator(int sort, String... filed) {
			this.sort = sort;
			this.filed = filed;
		}

		public ImComparator(int[] sorts, String[] filed) {
			this.sorts = sorts;
			this.filed = filed;
		}

		@SuppressWarnings("rawtypes")
		@Override
		public int compare(Object o1, Object o2) {
			int result = 0;
			for (int i = 0; i < filed.length; i++) {
				String file = filed[i];
				Object value1 = null;
				Object value2 = null;
				if (o1 instanceof Map && o2 instanceof Map) {
					value1 = ((Map) o1).get(file);
					value2 = ((Map) o2).get(file);
				} else {
					value1 = ReflexUtil.invokeMethod(file, o1);
					value2 = ReflexUtil.invokeMethod(file, o2);
				}
				if (value1 == null || value2 == null) {
					continue;
				}
				double v1;
				double v2;

				if(isNumber(value1)){
					v1 = Double.valueOf(value1.toString());
				}else{
					v1 = (int) value1.toString().charAt(0);//49
				}
				if(isNumber(value2)){
					v2 = Double.valueOf(value2.toString());
				}else{
					v2 = (int) value2.toString().charAt(0);//97
				}

				double vd = v1 - v2;
				if (vd == 0) continue;
				while (Math.abs(vd) < 1) {
					vd *= 10;
				}
				if (sorts != null && i < sorts.length) {
					if (sorts[i] == 0) continue;
					return (int) (sorts[i] * vd);
				}
				return (int) (sort * vd);
			}
			return result;
		}
	}

	/**
	 * 反射属性      更多的反射请参见    当前包d.ReflectBuild
	 *
	 * @author Hocean  @version 2016年9月6日 下午3:10:36.
	 */
	private static class ReflexUtil {
		public static Object invokeField(String fieldName, Object object) {
			Field field = null;
			try {
				field = object.getClass().getDeclaredField(fieldName);
				field.setAccessible(true); //反射私有属性 权限
			} catch (NoSuchFieldException | SecurityException e) {
				e.printStackTrace();
			} //属性
			Object vo = null;
			try {
				vo = field.get(object);
			} catch (IllegalArgumentException | IllegalAccessException e) {
			}
			return vo;
		}

		/**
		 * 反射 对象
		 *
		 * @param propertiesName
		 * @param object
		 * @return
		 */
		public static Object invokeMethod(String propertiesName, Object object) {
			try {
				if (object == null) return null;
				if (!propertiesName.contains(".")) {
					String methodName = "get" + getMethodName(propertiesName);
					Method method = object.getClass().getMethod(methodName);
					return method.invoke(object);
				}
				String methodName = "get" + getMethodName(propertiesName.substring(0, propertiesName.indexOf(".")));
				Method method = object.getClass().getMethod(methodName);
				return invokeMethod(propertiesName.substring(propertiesName.indexOf(".") + 1), method.invoke(object));
			} catch (Exception e) {
				return null;
			}
		}

		/**
		 * 得到一般驼峰命名法的  名字
		 *
		 * @param fildeName
		 * @return
		 */
		private static String getMethodName(String fildeName) {
			byte[] items = fildeName.getBytes();
			items[0] = (byte) ((char) items[0] - 'a' + 'A');
			return new String(items);
		}

	}

	/**
	 * 判斷字符串是否能  转换为int double 去空格
	 * @param o
	 * @return
	 */
	public static boolean isNumber(Object o) {
		if(o == null) return false;
		String str = o.toString().trim();
		int len = str.length();
		int po = 0;
		char point = '.';
		for (int i = len - 1; i >= 0; i--) {
			char c = str.charAt(i);
			if (!Character.isDigit(c) ) {
				if(c == point){
					po++;
				}else{
					return false;
				}
			}
		}
		if(po <= 1){
			return len > 1;
		}else{
			return false;
		}
	}


	//region 测试
	/**
	 * 测试使用
	 *
	 * @author Hocean  @version 2016年9月6日 下午3:10:36.
	 */
	private static class User {

		private Object id;
		private Object num;
		private Object name;

		public User(Object id, Object num, Object name) {
			this.id = id;
			this.num = num;
			this.name = name;
		}

		public Object getId() {
			return id;
		}

		public void setId(Object id) {
			this.id = id;
		}

		public Object getName() {
			return name;
		}

		public void setName(Object name) {
			this.name = name;
		}

		public Object getNum() {
			return num;
		}

		public void setNum(Object num) {
			this.num = num;
		}
	}

	/**
	 * 排序对象列表List<?> 或  集合列表List<Map<?, ?>>    (String Object 类型的字段  也可以进行排序)
	 *
	 * @author Hocean  @version 2016年9月6日 下午3:17:46.
	 */
	private static class CompareUtilTest {
		//Demo
		public static void main() {

			////排序对象列表
			List<User> list = new ArrayList<>();
			list.add(new User(1, "1", 0.1));
			list.add(new User(2, "3", 0.3));
			list.add(new User(4, "1", 0.2));
			list.add(new User(2, "2", 0.4));
			System.out.println("对象排序   依次按照 id num name 倒序排序  =>");
			CompareUtil.sort(list, ESort.desc, "id", "num", "name");
			for (User user : list) {
				System.out.println(user.getId() + " " + user.getNum() + " " + user.getName());
			}
			System.out.println();

			////排序集合列表
			List<Map<String, Object>> lists = new ArrayList<>();
			Map<String, Object> map = new HashMap<>();
			map.put("id", 1);
			map.put("num", "1");
			map.put("name", 0.1);
			lists.add(map);
			map = new HashMap<>();
			map.put("id", 2);
			map.put("num", "3");
			map.put("name", 0.3);
			lists.add(map);
			map = new HashMap<>();
			map.put("id", 4);
			map.put("num", "1");
			map.put("name", 0.3);
			lists.add(map);
			map = new HashMap<>();
			map.put("id", 3);
			map.put("num", "3");
			map.put("name", 0.4);
			lists.add(map);
			System.out.println("MAP排序   依次按照 id num name 正序排序  =>");
			CompareUtil.sort(lists, ESort.asc, "id", "num", "name");
			for (Map<String, Object> maps : lists) {
				System.out.println(maps.get("id") + " " + maps.get("num") + " " + maps.get("name"));
			}
			System.out.println();

			////复杂排序
			System.out.println("复杂排序     =>");
			System.out.println("  方法一   MAP排序   按num正序  如果相等  再按name倒序  =>");
			CompareUtil.sort(lists, new String[]{"num", "name"}, new ESort[]{ESort.asc, ESort.desc});
			for (Map<String, Object> maps : lists) {
				System.out.println("  " + maps.get("id") + " " + maps.get("num") + " " + maps.get("name"));
			}
			System.out.println();

			System.out.println("  方法二  MAP排序   按num正序  如果相等  再按name倒序  =>");
			Map<String, ESort> ms = new HashMap<>();
			ms.put("num", ESort.asc);
			ms.put("name", ESort.desc);
			CompareUtil.sort(lists, ms);
			for (Map<String, Object> maps : lists) {
				System.out.println("  " + maps.get("id") + " " + maps.get("num") + " " + maps.get("name"));
			}
			System.out.println();

			System.out.println("  方法三  MAP排序   按num正序  如果相等  再按name倒序  =>");
			CompareUtil.sortFS(lists, "num", "asc", "name", "desc");
			for (Map<String, Object> maps : lists) {
				System.out.println("  " + maps.get("id") + " " + maps.get("num") + " " + maps.get("name"));
			}
			System.out.println();
		}
	}
	//endregion
}



