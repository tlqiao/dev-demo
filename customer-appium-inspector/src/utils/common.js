const findElementUnderCursor = (x, y,pageSource) => {
    const isPointInside = (element, x, y) => {
        return (
            x >= element.x &&
            y >= element.y &&
            x <= element.x + element.width &&
            y <= element.y + element.height
        );
    };

    const findElement = (elements) => {
        for (const element of elements) {
            if (isPointInside(element, x, y)) {
                return element;
            }
            const child = findElement(element.children);
            if (child) {
                return child;
            }
        }
        return null;
    };

    return findElement(pageSource);
};
export {findElementUnderCursor}
