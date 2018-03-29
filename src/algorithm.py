def find_region(data, rows, cols, region, size):
    div = size / 2
    regions = []
    for j in range(div, (rows - div)):
        for i in range(div, (cols - div)):
            if region[div][div] != -1 and region[div][div] != data[j][i]:
                continue

            breaked = False
            for n in range(1, div + 1):
                if ((region[div - n][div] != -1 and
                        region[div - n][div] != data[j - n][i]) or
                    (region[div + n][div] != -1 and
                        region[div + n][div] != data[j + n][i]) or
                    (region[div][div - n] != -1 and
                        region[div][div - n] != data[j][i - n]) or
                    (region[div][div + n] != -1 and
                        region[div][div + n] != data[j][i + n])):
                    breaked = True;
                    break

                for m in range(1, div + 1):
                    if ((region[div - n][div - m] != -1 and
                            region[div - n][div - m] != data[j - n][i - m]) or
                        (region[div - n][div + m] != -1 and
                            region[div - n][div + m] != data[j - n][i + m]) or
                        (region[div + n][div + m] != -1 and
                            region[div + n][div + m] != data[j + n][i + m]) or
                        (region[div + n][div - m] != -1 and
                            region[div + n][div - m] != data[j + n][i - m])):
                        breaked = True;
                        break
                if breaked is True:
                    break
            if breaked is False:
                regions.append((j, i))
    return regions

def get_region_from_data(data, size, i, j):
    region = []
    div = size / 2
    min_j = j - div
    max_j = min_j + size

    for n in range(-div, div + 1):
        region.append(data[n + i][min_j:max_j])
    return region
    
def find_common_regions(prim_data, prim_rows, prim_cols, sec_data, sec_rows, sec_cols):
    if prim_rows < 3 or prim_cols < 3 or sec_rows < 3 or sec_cols < 3:
        print "Data should have at least size 3 in each dimention."
        raise ValueError("Dimention size less then 3")

    result = []
    size = 3
    for i in range(1, prim_rows - 1):
        for j in range(1, prim_cols - 1):
            region = get_region_from_data(prim_data, 3, i, j)
            out = find_region(sec_data, sec_rows, sec_cols, region, 3)
            if out : result.append(out)

    return result
