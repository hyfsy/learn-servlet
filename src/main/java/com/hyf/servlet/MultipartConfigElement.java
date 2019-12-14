package com.hyf.servlet;

import com.hyf.servlet.annotation.MultipartConfig;

/**
 * 一个{@link MultipartConfig}注解值的Java类表示
 *
 * @see ServletRegistration.Dynamic#setMultipartConfig
 * @since 3.0
 */
public class MultipartConfigElement {

    private String location;
    private long maxFileSize;
    private long maxRequestSize;
    private int fileSizeThreshold;

    /**
     * 除了location，为其他成员变量构造一个默认值的实例
     *
     * @param location 如果location为null，则变成默认的 ""
     */
    public MultipartConfigElement(String location) {
        if (location == null) {
            this.location = "";
        } else {
            this.location = location;
        }
        this.maxFileSize = -1L;
        this.maxRequestSize = -1L;
        this.fileSizeThreshold = 0;
    }

    /**
     * 使用指定的所有值构造实例
     *
     * @param location          存储文件的目录位置
     * @param maxFileSize       允许上传文件的最大大小
     * @param maxRequestSize    允许上传文件的multipart/form-data请求的最大大小
     * @param fileSizeThreshold 文件写入磁盘后的大小阈值
     */
    public MultipartConfigElement(String location, long maxFileSize, long maxRequestSize, int fileSizeThreshold) {
        if (location == null) {
            this.location = "";
        } else {
            this.location = location;
        }
        this.maxFileSize = maxFileSize;
        this.maxRequestSize = maxRequestSize;
        this.fileSizeThreshold = fileSizeThreshold;
    }

    /**
     * 从{@link MultipartConfig}注解值构造一个实例
     *
     * @param multipartConfig 实体类对应的注解对象
     */
    public MultipartConfigElement(MultipartConfig multipartConfig) {
        this.location = multipartConfig.location();
        this.maxFileSize = multipartConfig.maxFileSize();
        this.maxRequestSize = multipartConfig.maxRequestSize();
        this.fileSizeThreshold = multipartConfig.fileSizeThreshold();
    }

    /**
     * 获取存储文件的目录位置
     *
     * @return 存储文件的目录位置
     */
    public String getLocation() {
        return location;
    }

    /**
     * 获取允许上传文件的最大大小
     *
     * @return 允许上传文件的最大大小
     */
    public long getMaxFileSize() {
        return maxFileSize;
    }

    /**
     * 获取允许上传文件的multipart/form-data请求的最大大小
     *
     * @return 允许上传文件的multipart/form-data请求的最大大小
     */
    public long getMaxRequestSize() {
        return maxRequestSize;
    }

    /**
     * 获取文件写入磁盘后的大小阈值
     *
     * @return 文件写入磁盘后的大小阈值
     */
    public int getFileSizeThreshold() {
        return fileSizeThreshold;
    }
}
