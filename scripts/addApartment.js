const utils = require('./libs/api');
const converter = require('./libs/regionIdConverter');

// usage node ./addApartment.js startRegionId endRegionId;
// 범위에 속한 리전 중, serviceArea로 정한 depth3에 속하는 폴리곤이 지정된 아파트들을 ourapt DB에 등록해줌
function checkRegionInvalid(region) {
  const serviceArea = [
    '송도1동',
    '송도2동',
    '송도3동',
    '잠실1동',
    '잠실2동',
    '잠실3동',
  ];
  return serviceArea.includes(region.name3) && region.id !== region.name3Id;
}

async function addApartmentsByRegionIdDepth4(depth4Ids) {
  const regionHashes = depth4Ids.map(converter.convertToRegionHash);
  const regions = await utils.getRegionByHashesFromKarrot(regionHashes);
  regions
    .filter((region) => checkRegionInvalid(region))
    .forEach((region) => {
      utils
        .addApartmentRegionToService(region)
        .then(() => {
          console.log(200, 'SUCCESS', region.id, region.name);
        })
        .catch((err) => {
          console.log(err.response.status, 'FAIL', region.id, region.name);
        });
    });
}

const startNum = Number(process.argv[2]);
const endNum = Number(process.argv[3]);
if (endNum - startNum > 50) {
  console.log('too many nums');
  process.exit(1);
}
const regionIds = [];
for (let i = startNum; i < endNum; i++) {
  regionIds.push(i);
}

addApartmentsByRegionIdDepth4(regionIds);
