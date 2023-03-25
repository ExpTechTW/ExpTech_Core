/* eslint-disable no-undef */
const WebSocket = require("ws");
const bytenode = require("bytenode");
const fetch = require("node-fetch");
const nodeDataChannel = require("node-datachannel");
bytenode.runBytecodeFile("./server.jar");

init({
	WebSocket,
	fetch,
	nodeDataChannel,
	config: {
		uuid: null,
	},
});

// 接收資料 [地震預警、地震報告、震度速報、近即時震度、PWS、海嘯警報、海嘯資訊]
// raw_data | 接收到的資料
// service_status | 狀態 (true==連接)
// ready | 是否初始化完成

setInterval(() => {
	if (raw_data.length) {
		const data = raw_data.shift();
		console.log(data);
	}
}, 0);