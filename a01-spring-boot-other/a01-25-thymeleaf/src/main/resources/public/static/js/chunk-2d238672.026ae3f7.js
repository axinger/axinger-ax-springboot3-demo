(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-2d238672"],{feda:function(t,e,n){"use strict";n.r(e);var o=function(){var t=this,e=t._self._c;return e("div",[e("h3",[t._v("测试页面")]),e("button",{on:{click:t.pinia}},[t._v("pinia")]),e("el-input",{attrs:{label:"文字"},model:{value:t.text,callback:function(e){t.text=e},expression:"text"}}),e("router-link",{attrs:{to:"/test/testPinia"}},[e("el-button",{attrs:{type:"primary",size:"small",icon:"el-icon-edit"}},[t._v(" Edit ")])],1)],1)},i=[],a={name:"TestPage",components:{},props:{},data:function(){return{text:null}},created:function(){console.log("created======================")},mounted:function(){console.log("mounted======================")},methods:{pinia:function(){this.$router.push({path:"/test/testPinia"})}}},s=a,c=n("2877"),l=Object(c["a"])(s,o,i,!1,null,"1584349c",null);e["default"]=l.exports}}]);