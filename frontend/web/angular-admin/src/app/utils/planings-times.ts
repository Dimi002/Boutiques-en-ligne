export function planingsTimesMaker(startAt: number, endAt: number): { order: number, time: string }[] {
    var result: { order: number, time: string }[] = [];
    if (startAt < endAt) {
        let order = 0;
        for (let i = startAt; i <= endAt; i++) {
            let suffixe = ' PM';
            let index = i - 12;
            if (i <= 12) {
                suffixe = ' AM';
                index = i;
            }
            const data00: { order: number, time: string } = {
                order: order,
                time: (index < 10 ? '0' : '') + index + ':00' + suffixe,
            }

            const data30: { order: number, time: string } = {
                order: order + 1,
                time: (index < 10 ? '0' : '') + index + ':30' + suffixe,
            }
            result.push(data00);
            if (i < endAt)
                result.push(data30);

            order += 2;
        }
        return result;
    }
    return [];
}
