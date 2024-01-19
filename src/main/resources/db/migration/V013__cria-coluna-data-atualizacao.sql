alter table forma_pagamento add data_atualizacao datetime null;
update forma_pagamento  set data_atualizacao = UTC_TIMESTAMP ;
ALTER table forma_pagamento modify data_atualizacao datetime not null;