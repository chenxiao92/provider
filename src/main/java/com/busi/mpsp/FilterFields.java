package com.busi.mpsp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

public class FilterFields {
	/**
	 * jsonz字符串过滤指定字段
	 *
	 * @param body 请求报文
	 * @param field 需要过滤的字段
	 * @return String 过滤后的报文打印
	 * @throws IOException IO异常
	 * @throws JsonProcessingException JSON转换执行异常
	 */
	public static String filterJsonField(String body, String field) throws JsonProcessingException, IOException {
		// 转换为jackson对象
		ObjectMapper mapper = new ObjectMapper();
		JsonNode bodyNode = mapper.readTree(body);
		//遍历节点屏蔽指定字段打印
		walker(field, bodyNode);
		return mapper.writeValueAsString(bodyNode);
	}

	/**
	 * json遍历并修改指定key的值
	 *
	 * @param field 指定需要修改值的字段
	 * @param node json对象
	 */
	public static void walker(String field, JsonNode node) {
		String key = null;
		if (node.isObject()) {
			Iterator<Map.Entry<String, JsonNode>> iterator = node.fields();

			while(iterator.hasNext()) {
				Map.Entry<String, JsonNode> entry = iterator.next();
				key = entry.getKey();
				if(field.equals(key)) {
					((ObjectNode)node).put(key, "***");
					break;
				} else {
					walker(field, entry.getValue());
				}
			}
		} else if (node.isArray()) {
			Iterator<JsonNode> arrayItemsIterator = node.elements();
			while(arrayItemsIterator.hasNext()) {
				JsonNode jsonNode = arrayItemsIterator.next();
				walker(field, jsonNode);
			}
		} 
	}
}
