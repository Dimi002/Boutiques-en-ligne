module.exports = {
  lastIndex: lastIndex,
  findInList: findInList,
}

/**
 *
 * @param {object} data
 * @returns
 */
function lastIndex(data) {
  var pg = data[0].id, i = 0;
  for(i =0; i< data.length; i++){
    if(data[i].id > pg) {
      pg = data[i].id
    }
  }

  return  pg;
}

/**
 *
 * @param {Array} list
 * @param {number} id
 * @returns
 */
function findInList(list, id) {
  var searchedItem = null, i=0;
  for(i =0; i< list.length; i++){
    if(list[i].id == id) {
      searchedItem = list[i];
      break;
    }
  }
  return  searchedItem;
}


