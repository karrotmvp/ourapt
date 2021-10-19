const axios = require('axios');
const { dict } = require('./regionHashDict');
require('dotenv').config();

const API_KEY = process.env.API_KEY;
const OAPI_KARROT_BASE_URL = 'https://oapi.kr.karrotmarket.com';
const BASE_URL = 'http://localhost:5000';
const DEBUG = false;

const commonHeader = {
  'Content-Type': 'application/json',
};

axios
  .get(OAPI_KARROT_BASE_URL + '/api/v2/regions/by_ids', {
    headers: {
      ...commonHeader,
      'X-API-Key': API_KEY,
    },
    params: {
      ids: Object.keys(dict).join(','),
    },
  })
  .then((response) => {
    const regions = response.data.data.regions.sort();

    if (DEBUG) {
      console.log(regions);
      return;
    }
    regions.forEach((region) => {
      axios
        .post(
          BASE_URL + '/api/v1/apartment',
          {
            id: null,
            keyName: region.name,
            channelName: null,
            channelDepthLevel: 0,
            nameDepth1: region.name1,
            regionHashDepth1: region.name1Id,
            nameDepth2: region.name2,
            regionHashDepth2: region.name2Id,
            nameDepth3: region.name3,
            regionHashDepth3: region.name3Id,
            nameDepth4: region.id !== region.name3Id ? region.name : null,
            regionHashDepth4: region.id !== region.name3Id ? region.id : null,
          },
          {
            headers: {
              ...commonHeader,
              Authorization: 'Bearer jacob',
            },
          }
        )
        .then((response) => {
          console.log(response.status);
        })
        .catch((err) => {
          console.log(err);
          process.exit(1);
        });
    });
  })
  .catch((err) => {
    console.log(err);
  });
