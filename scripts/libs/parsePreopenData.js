const { dict, getNameByRegionId } = require('./regionHashDict');
const data = [
  {
    region_id: '0601eb4940ba',
    'IFNULL(물어볼래요, 0)': 1,
    'IFNULL(대답할래요, 0)': 1,
    'IFNULL(그냥구경,0)': 1,
  },
  {
    region_id: '0b96cc858bf6',
    'IFNULL(물어볼래요, 0)': 11,
    'IFNULL(대답할래요, 0)': 13,
    'IFNULL(그냥구경,0)': 12,
  },
  {
    region_id: '1f0758ccde06',
    'IFNULL(물어볼래요, 0)': 10,
    'IFNULL(대답할래요, 0)': 9,
    'IFNULL(그냥구경,0)': 9,
  },
  {
    region_id: '21048a8304cf',
    'IFNULL(물어볼래요, 0)': 1,
    'IFNULL(대답할래요, 0)': 1,
    'IFNULL(그냥구경,0)': 3,
  },
  {
    region_id: '2b6112932ec1',
    'IFNULL(물어볼래요, 0)': 1,
    'IFNULL(대답할래요, 0)': 1,
    'IFNULL(그냥구경,0)': 1,
  },
  {
    region_id: '3167ae4b772a',
    'IFNULL(물어볼래요, 0)': 1,
    'IFNULL(대답할래요, 0)': 1,
    'IFNULL(그냥구경,0)': 1,
  },
  {
    region_id: '36dc06db13e2',
    'IFNULL(물어볼래요, 0)': 1,
    'IFNULL(대답할래요, 0)': 1,
    'IFNULL(그냥구경,0)': 1,
  },
  {
    region_id: '37ac0da953d5',
    'IFNULL(물어볼래요, 0)': 2,
    'IFNULL(대답할래요, 0)': 2,
    'IFNULL(그냥구경,0)': 2,
  },
  {
    region_id: '471abc99b378',
    'IFNULL(물어볼래요, 0)': 1,
    'IFNULL(대답할래요, 0)': 1,
    'IFNULL(그냥구경,0)': 1,
  },
  {
    region_id: '5b1a7c427f53',
    'IFNULL(물어볼래요, 0)': 0,
    'IFNULL(대답할래요, 0)': 0,
    'IFNULL(그냥구경,0)': 0,
  },
  {
    region_id: '6a7eefda7865',
    'IFNULL(물어볼래요, 0)': 25,
    'IFNULL(대답할래요, 0)': 26,
    'IFNULL(그냥구경,0)': 24,
  },
  {
    region_id: '87ea17bc48db',
    'IFNULL(물어볼래요, 0)': 1,
    'IFNULL(대답할래요, 0)': 1,
    'IFNULL(그냥구경,0)': 1,
  },
  {
    region_id: '996bc98b6583',
    'IFNULL(물어볼래요, 0)': 1,
    'IFNULL(대답할래요, 0)': 1,
    'IFNULL(그냥구경,0)': 2,
  },
  {
    region_id: 'a87002cc41f1',
    'IFNULL(물어볼래요, 0)': 16,
    'IFNULL(대답할래요, 0)': 12,
    'IFNULL(그냥구경,0)': 14,
  },
  {
    region_id: 'b7ca1e49757c',
    'IFNULL(물어볼래요, 0)': 1,
    'IFNULL(대답할래요, 0)': 0,
    'IFNULL(그냥구경,0)': 0,
  },
  {
    region_id: 'bf0ad88cdb07',
    'IFNULL(물어볼래요, 0)': 0,
    'IFNULL(대답할래요, 0)': 0,
    'IFNULL(그냥구경,0)': 0,
  },
  {
    region_id: 'bfe5ece31a26',
    'IFNULL(물어볼래요, 0)': 0,
    'IFNULL(대답할래요, 0)': 0,
    'IFNULL(그냥구경,0)': 0,
  },
  {
    region_id: 'cc1856fff005',
    'IFNULL(물어볼래요, 0)': 1,
    'IFNULL(대답할래요, 0)': 0,
    'IFNULL(그냥구경,0)': 1,
  },
  {
    region_id: 'ce58d8005285',
    'IFNULL(물어볼래요, 0)': 0,
    'IFNULL(대답할래요, 0)': 0,
    'IFNULL(그냥구경,0)': 0,
  },
  {
    region_id: 'd3b0f3c81c7b',
    'IFNULL(물어볼래요, 0)': 1,
    'IFNULL(대답할래요, 0)': 1,
    'IFNULL(그냥구경,0)': 1,
  },
  {
    region_id: 'd4e05686095b',
    'IFNULL(물어볼래요, 0)': 1,
    'IFNULL(대답할래요, 0)': 0,
    'IFNULL(그냥구경,0)': 0,
  },
  {
    region_id: 'f9ea71209dee',
    'IFNULL(물어볼래요, 0)': 0,
    'IFNULL(대답할래요, 0)': 0,
    'IFNULL(그냥구경,0)': 0,
  },
  {
    region_id: 'fe82298e3c63',
    'IFNULL(물어볼래요, 0)': 2,
    'IFNULL(대답할래요, 0)': 2,
    'IFNULL(그냥구경,0)': 3,
  },
];

const data2 = [
  {
    region_id: null,
    'count(*)': 3,
  },
  {
    region_id: '0601eb4940ba',
    'count(*)': 1,
  },
  {
    region_id: '0b96cc858bf6',
    'count(*)': 20,
  },
  {
    region_id: '1f0758ccde06',
    'count(*)': 19,
  },
  {
    region_id: '21048a8304cf',
    'count(*)': 3,
  },
  {
    region_id: '2b6112932ec1',
    'count(*)': 1,
  },
  {
    region_id: '3167ae4b772a',
    'count(*)': 1,
  },
  {
    region_id: '36dc06db13e2',
    'count(*)': 1,
  },
  {
    region_id: '37ac0da953d5',
    'count(*)': 3,
  },
  {
    region_id: '471abc99b378',
    'count(*)': 1,
  },
  {
    region_id: '5b1a7c427f53',
    'count(*)': 1,
  },
  {
    region_id: '6a7eefda7865',
    'count(*)': 33,
  },
  {
    region_id: '87ea17bc48db',
    'count(*)': 1,
  },
  {
    region_id: '996bc98b6583',
    'count(*)': 5,
  },
  {
    region_id: 'a87002cc41f1',
    'count(*)': 29,
  },
  {
    region_id: 'b7ca1e49757c',
    'count(*)': 2,
  },
  {
    region_id: 'bf0ad88cdb07',
    'count(*)': 2,
  },
  {
    region_id: 'bfe5ece31a26',
    'count(*)': 1,
  },
  {
    region_id: 'cc1856fff005',
    'count(*)': 1,
  },
  {
    region_id: 'ce58d8005285',
    'count(*)': 1,
  },
  {
    region_id: 'd3b0f3c81c7b',
    'count(*)': 1,
  },
  {
    region_id: 'd4e05686095b',
    'count(*)': 1,
  },
  {
    region_id: 'f9ea71209dee',
    'count(*)': 3,
  },
  {
    region_id: 'fe82298e3c63',
    'count(*)': 4,
  },
];

function labelToPreopenData(preopenData) {
  return preopenData.map((data) => ({
    ...data,
    region_name: getNameByRegionId(data.region_id),
  }));
}

function toCSV(sourceData) {
  let csv = '';
  csv += 'region_name,region_id,물어볼래요,대답할래요,그냥구경\n';
  sourceData.forEach((data) => {
    csv += `${data.region_name},${data.region_id},${data['IFNULL(물어볼래요, 0)']},${data['IFNULL(대답할래요, 0)']},${data['IFNULL(그냥구경,0)']}\n`;
  });
  return csv;
}

function toCSV2(sourceData) {
  let csv = '';
  csv += 'region_name,region_id,사전오픈예약수\n';
  sourceData.forEach((data) => {
    csv += `${data.region_name},${data.region_id},${data['count(*)']}\n`;
  });
  return csv;
}

console.log(toCSV2(labelToPreopenData(data2)));
