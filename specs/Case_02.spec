Case_02
=====================
Created by okan on 20.09.2021

This is an executable specification file which follows markdown syntax.
Every heading in this file denotes a scenario. Every bulleted point denotes a step.
     
Login_Case
----------------
* Wait "3" seconds
* Does "btn" element Exist
* Click element by "Trendyol_girisBtn"
* Wait "1" seconds
* Click element by "Trendyol_emailBtn"
* Wait "1" seconds
* Find element by "Trendyol_emailBtn" and send keys "hwid.turkey@gmail.com"
* Wait "1" seconds
* Click element by "Trendyol_psswrdBtn"
* Wait "1" seconds
* Find element by "Trendyol_psswrdBtn" and send keys "Admin13!"
* Wait "1" seconds
* Click element by "Trendyol_girisUserBtn"
* Wait "1" seconds
* Does "Trendyol_dateBar" element Exist


Fail_Login_Case
--------------

* Wait "3" seconds
* Does "btn" element Exist
* Click element by "Trendyol_girisBtn"
* Wait "1" seconds
* Click element by "Trendyol_emailBtn"
* Wait "1" seconds
* Find element by "Trendyol_emailBtn" and send keys "de.support@gmail.com"
* Wait "1" seconds
* Click element by "Trendyol_psswrdBtn"
* Wait "1" seconds
* Find element by "Trendyol_psswrdBtn" and send keys "Admin13!"
* Wait "1" seconds
* Click element by "Trendyol_girisUserBtn"
* Wait "1" seconds
* Does "Trendyol_hataMsg" element Exist