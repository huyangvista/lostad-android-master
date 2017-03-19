package com.lostad.app.demo.util.vdll.data.msql;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * 数据库注解 提取
 * @author Hocean
 *
 */
public class VTag implements Iterable<VTag.VTagRow>
{
	private VTagRow[] vatrow = null;
	private VTagRow[] vatrowKey = null;

	ETag etag = ETag.tag;

	public List<VTagRow> valRow = new ArrayList<>(); //所有带标记
	public List<VTagRow> valRowKey = new ArrayList<>(); //所有主键
	public List<VTagRow> valRowkeyBase = new ArrayList<>(); //只有基础主键 (约等于 没有AI的主键)
	public List<VTagRow> valRowNoKey = new ArrayList<>(); //没有任何主键
	public List<VTagRow> valRowNoAi = new ArrayList<>();  //仅没有自增的主键

	@Deprecated
	public VTag(Class<?> cls, ETag etag)
	{
		this.etag = etag;
		/*if (cls.isAnnotationPresent(ITag.class))  //反射类标记参数
		{
			ITag itag = cls.getAnnotation(ITag.class);
			System.err.println(itag.value());
		}*/

		//Field[] fields = VReflect.getFields(cls);

		Field[] fields = cls.getDeclaredFields(); //属性
		vatrow = new VTagRow[fields.length];
		vatrowKey = new VTagRow[fields.length];
		int vicountNew = 0;
		int vicountNewKey = 0;
		for (int i = 0; i < fields.length; i++)
		{
			Field field = fields[i]; //第几个参数			
			field.setAccessible(true); //反射私有属性 权限			
			Class<?> type = field.getType(); //反射类型
			String typeName = type.getName();//得到类型
			String fieldName = field.getName(); //反射字段

			VTagRow vtr = new VTagRow();
			vtr.vstype = typeName;
			vtr.vsname = fieldName;
			//vtr.vsvalue = fieldName;
			if (field.isAnnotationPresent(AITag.class))
			{
				vtr.aitag = field.getAnnotation(AITag.class);
				
			}

			

			switch (etag)
			{
				case tag:
					if (field.isAnnotationPresent(AITag.class))
					{
						vatrow[vicountNew++] = vtr;
					}
					break;
				case tagNoKey:
					if (field.isAnnotationPresent(AITag.class))
					{
						//如果是主键
						if (vtr.aitag.vsql().equals(VSTag._PK))
							vatrowKey[vicountNewKey++] = vtr;
						else vatrow[vicountNew++] = vtr;
					}
					break;
				case tagNoAiKey:
					if (field.isAnnotationPresent(AITag.class))
					{
						//如果是主键
						if (vtr.aitag.vsqlValue().equals(VSTag._PK)) vatrow[vicountNew++] = vtr;
					}
					break;
				case tagKey:
					if (field.isAnnotationPresent(AITag.class))
					{
						//如果是主键
						if (vtr.aitag.vsql().equals(VSTag._PK)) vatrow[vicountNew++] = vtr;
						//else vatrow[vicountNew++] = vtr;
					}
					break;
				case all:
					vatrow[vicountNew++] = vtr;
					break;
				default:
					vatrow[i] = vtr;
					break;
			}

			/*try
			{
				Object value = field.get(t); //字段值			
			}
			catch (IllegalArgumentException | IllegalAccessException e1)
			{
				e1.printStackTrace();
			}*/
		}
		vatrow = Arrays.copyOf(vatrow, vicountNew);
		vatrowKey = Arrays.copyOf(vatrowKey, vicountNewKey);

	}

	public VTag(Class<?> cls)
	{
		Field[] fields = cls.getDeclaredFields(); //属性
		vatrow = new VTagRow[fields.length];

		int vicountNew = 0;
		for (int i = 0; i < fields.length; i++)
		{
			Field field = fields[i]; //第几个参数			
			if (field.isAnnotationPresent(AITag.class)) //含有标记
			{
				field.setAccessible(true); //反射私有属性 权限
				Class<?> type = field.getType(); //反射类型
				String typeName = type.getName();//得到类型
				String fieldName = field.getName(); //反射字段 				
				VTagRow vtr = new VTagRow();
				vtr.vstype = typeName;
				vtr.vsname = fieldName;
				//vtr.vovalue = fieldName;
				vtr.aitag = field.getAnnotation(AITag.class);
				
				if (vtr.aitag.vsqlValue() .equals(VSTag._PK))
				{
					valRowNoAi.add(vtr);
				}
				switch (vtr.aitag.vsql())
				{
					case "":
						valRowNoKey.add(vtr);
						break;
					case VSTag._PK:
						valRowKey.add(vtr);
						if (vtr.aitag.vsqlValue() .equals(VSTag._PK))
						{
							valRowkeyBase .add(vtr);
						}
						else if (vtr.aitag.vsqlValue() .equals(VSTag._PK + VSTag._AI))
						{
							
						}
						break;

					default:
						break;
				}
				valRow.add(vtr);
				
				vatrow[vicountNew] = vtr;
				vicountNew++;
			}
		}
		vatrow = Arrays.copyOf(vatrow, vicountNew);
	}

	public VTag(Object vo)
	{
		Field[] fields = vo.getClass().getDeclaredFields(); //属性
		vatrow = new VTagRow[fields.length];

		int vicountNew = 0;
		for (int i = 0; i < fields.length; i++)
		{
			Field field = fields[i]; //第几个参数			
			if (field.isAnnotationPresent(AITag.class)) //含有标记
			{
				field.setAccessible(true); //反射私有属性 权限
				Class<?> type = field.getType(); //反射类型
				String typeName = type.getName();//得到类型
				String fieldName = field.getName(); //反射字段 				
				VTagRow vtr = new VTagRow();
				vtr.vstype = typeName;
				vtr.vsname = fieldName;
				try
				{
					vtr.vovalue = field.get(vo);
				}
				catch (Exception e)
				{					
				}
				vtr.aitag = field.getAnnotation(AITag.class);				
				if (vtr.aitag.vsqlValue() .equals(VSTag._PK))
				{
					valRowNoAi.add(vtr);
				}
				switch (vtr.aitag.vsql())
				{
					case "":
						valRowNoKey.add(vtr);
						break;
					case VSTag._PK:
						valRowKey.add(vtr);
						if (vtr.aitag.vsqlValue() .equals(VSTag._PK))
						{
							valRowkeyBase .add(vtr);
						}
						else if (vtr.aitag.vsqlValue() .equals(VSTag._PK + VSTag._AI))
						{
							
						}
						break;

					default:
						break;
				}
				valRow.add(vtr);
				
				vatrow[vicountNew] = vtr;
				vicountNew++;
			}
		}
		vatrow = Arrays.copyOf(vatrow, vicountNew);
	}
	
	/*public VTagRow get(int i)
	{
		return vatrow[i];
	}

	public VTagRow getKey(int i)
	{
		return vatrowKey[i];
	}

	public void set(int i, VTagRow vtr)
	{
		vatrow[i] = vtr;
	}

	public int count()
	{
		int count = 0;
		count = vatrow.length;
		return count;
	}

	public int countKey()
	{
		int count = 0;
		count = vatrowKey.length;
		return count;
	}*/

	//每行字段的信息
	public class VTagRow
	{
		public String vstype;  //类型
		public String vsname;  //字段
		public Object vovalue;  //值
		public AITag aitag;  //注解
	}

	@Override
	public Iterator<VTagRow> iterator()
	{
		// TODO Auto-generated method stub
		return new VIterator();
	}

	public class VIterator implements Iterator<VTagRow>
	{
		private int vindex = 0;

		@Override
		public boolean hasNext()
		{
			// TODO Auto-generated method stub
			return vindex != vatrow.length;
		}

		@Override
		public VTagRow next()
		{
			// TODO Auto-generated method stub
			return vatrow[vindex++];
		}

		@Override
		public void remove()
		{
			// TODO: Implement this method
		}

	
		

	}

}

/*//USE 使用方法 
VTag vtag = new VTag(User.class);

for (VTag.VTagRow vTagRow : vtag)
{
	if(vTagRow.itag != null)
	{
		System.err.println(vTagRow.vsname);
		System.err.println(vTagRow.vstype);
		System.err.println(vTagRow.vovalue);
		System.err.println(vTagRow.itag.value());

	}

}
*/

//foreatch 
/*List<ITag> vlist =  VReflect.getTag(User.class, ITag.class);

for (ITag iTag : vlist)
{
	System.err.println(iTag.id());
	System.err.println(iTag.value());
}*/

//User user = new User();

/*Field[] fields = VReflect.getFields(User.class);
for (Field field : fields)
{
	if (field.isAnnotationPresent(VTest.class))
	{
		VTest tag = (VTest) field.getAnnotation(VTest.class);
		tag.value();
		System.out.println(tag.id());
	}
	
}*/

/*
public static void trackUseCases(List useCases, Class cl)
{
	for (Method m : cl.getDeclaredMethods())
	{
		UseCase uc = m.getAnnotation(UseCase.class);
		if (uc != null)
		{
			System.out.println("Found Use Case:" + uc.id() + " " + uc.description());
			useCases.remove(new Integer(uc.id()));
		}
	}
	for (int i : useCases)
	{
		System.out.println("Warning: Missing use case-" + i);
	}
}

//快速添加 列表
@SuppressWarnings("unchecked")
public static void main(String[] args)
{
	@SuppressWarnings("rawtypes")
	List useCases = new ArrayList();
	Collections.addAll(useCases, 47, 48, 49, 50);
	trackUseCases(useCases, PasswordUtils.class);
}*/
