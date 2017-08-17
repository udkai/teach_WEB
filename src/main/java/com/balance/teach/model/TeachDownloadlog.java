package com.balance.teach.model;

import java.util.Date;

/**
 * 教学资源下载记录
 * 
 * @author dyq
 *
 */
public class TeachDownloadlog {

	private Integer id;// ID
	private Integer repository_id;//
	private String title;// 标题
	private String file_name;// 文件名
	private String download_user_name;// 下载人
	private Integer download_user_id;// 下载人ID
	private Date download_date;// 下载时间
	private String ip;// IP
	private String note;// 备注

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getRepository_id() {
		return repository_id;
	}

	public void setRepository_id(Integer repository_id) {
		this.repository_id = repository_id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDownload_user_name() {
		return download_user_name;
	}

	public void setDownload_user_name(String download_user_name) {
		this.download_user_name = download_user_name;
	}

	public Integer getDownload_user_id() {
		return download_user_id;
	}

	public void setDownload_user_id(Integer download_user_id) {
		this.download_user_id = download_user_id;
	}

	public Date getDownload_date() {
		return download_date;
	}

	public void setDownload_date(Date download_date) {
		this.download_date = download_date;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getFile_name() {
		return file_name;
	}

	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}
}
